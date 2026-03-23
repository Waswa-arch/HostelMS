package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;
import com.hostelms.utils.SessionManager;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * REFACTORED from original LoginActivity.
 *
 * Changes made:
 *  - Student login now uses Registration Number (not email) per spec.
 *  - Tries PHP API first; falls back to local Room DB when server is unreachable.
 *  - Admin login still works as before (email/password via local DB or API).
 *  - ProgressBar added for network feedback.
 *  - RoleSelectActivity entry point retained.
 */
public class LoginActivity extends AppCompatActivity {
    private String role;
    private ProgressBar progressBar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        role = getIntent().getStringExtra("role");

        TextView tvTitle = findViewById(R.id.tvLoginTitle);
        tvTitle.setText("admin".equals(role) ? "Admin Login" : "Student Login");

        // For students: label says "Registration Number", for admin: "Email / Username"
        TextView tvEmailLabel = findViewById(R.id.tvEmailLabel);
        EditText etEmail      = findViewById(R.id.etEmail);
        if ("student".equals(role)) {
            tvEmailLabel.setText("REGISTRATION NUMBER");
            etEmail.setHint("e.g. SCT/2023/001");
            etEmail.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        }

        EditText etPassword = findViewById(R.id.etPassword);
        btnLogin            = findViewById(R.id.btnLogin);
        progressBar         = findViewById(R.id.progressBar);
        TextView tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setVisibility("student".equals(role) ? View.VISIBLE : View.GONE);
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String credential = etEmail.getText().toString().trim();
            String pass       = etPassword.getText().toString().trim();
            if (credential.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("admin".equals(role)) loginAdmin(credential, pass);
            else                      loginStudent(credential, pass);
        });
    }

    // ── Student login: try API → fallback to Room DB ──────────────────────
    private void loginStudent(String regNumber, String password) {
        setLoading(true);
        Map<String, String> params = new HashMap<>();
        params.put("reg_number", regNumber);
        params.put("password",   password);

        ApiClient.post(this, ApiConfig.LOGIN, params, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                setLoading(false);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("success".equals(obj.getString("status"))) {
                        JSONObject u = obj.getJSONObject("user");
                        new SessionManager(LoginActivity.this).createSession(
                                u.getInt("id"), "student",
                                u.getString("name"), u.getString("email"));
                        goToStudentDashboard();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                obj.optString("message", "Invalid credentials"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) { fallbackStudentLogin(regNumber, password); }
            }
            @Override public void onError(String error) {
                // Server not reachable – use local Room DB
                fallbackStudentLogin(regNumber, password);
            }
        });
    }

    private void fallbackStudentLogin(String regNumber, String password) {
        setLoading(false);
        AppDatabase db = AppDatabase.getInstance(this);
        Student s = db.studentDao().getByRegNumber(regNumber);
        if (s == null || !password.equals(s.password) || !"student".equals(s.role)) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            return;
        }
        new SessionManager(this).createSession(s.id, "student", s.name, s.email);
        goToStudentDashboard();
    }

    // ── Admin login: try API → fallback to Room DB ────────────────────────
    private void loginAdmin(String credential, String password) {
        setLoading(true);
        Map<String, String> params = new HashMap<>();
        params.put("username", credential);
        params.put("password", password);

        ApiClient.post(this, ApiConfig.ADMIN_LOGIN, params, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                setLoading(false);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("success".equals(obj.getString("status"))) {
                        JSONObject a = obj.getJSONObject("admin");
                        new SessionManager(LoginActivity.this).createSession(
                                a.getInt("id"), "admin",
                                a.getString("name"), a.optString("email", ""));
                        goToAdminDashboard();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                obj.optString("message", "Invalid credentials"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) { fallbackAdminLogin(credential, password); }
            }
            @Override public void onError(String error) {
                fallbackAdminLogin(credential, password);
            }
        });
    }

    private void fallbackAdminLogin(String email, String password) {
        setLoading(false);
        AppDatabase db = AppDatabase.getInstance(this);
        Student s = db.studentDao().login(email, password);
        if (s == null || !"admin".equals(s.role)) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            return;
        }
        new SessionManager(this).createSession(s.id, "admin", s.name, s.email);
        goToAdminDashboard();
    }

    private void goToStudentDashboard() {
        startActivity(new Intent(this, StudentDashboardActivity.class));
        finish();
    }
    private void goToAdminDashboard() {
        startActivity(new Intent(this, AdminDashboardActivity.class));
        finish();
    }
    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!loading);
    }
}
