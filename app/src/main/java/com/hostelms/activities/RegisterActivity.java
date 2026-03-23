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
 * REFACTORED from original RegisterActivity.
 *
 * Changes made:
 *  - Fields now match spec: name, registration number, phone, email, password.
 *  - Age/gender/course fields retained (bonus fields, kept from original).
 *  - Tries PHP register.php API first; falls back to Room DB.
 *  - ProgressBar for loading state.
 */
public class RegisterActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etName     = findViewById(R.id.etName);
        EditText etAge      = findViewById(R.id.etAge);
        Spinner  spGender   = findViewById(R.id.spGender);
        EditText etCourse   = findViewById(R.id.etCourse);
        EditText etRegNo    = findViewById(R.id.etRegNo);
        EditText etEmail    = findViewById(R.id.etEmail);
        EditText etPhone    = findViewById(R.id.etPhone);
        EditText etPassword = findViewById(R.id.etPassword);
        btnRegister         = findViewById(R.id.btnRegister);
        progressBar         = findViewById(R.id.progressBar);
        TextView tvLogin    = findViewById(R.id.tvLogin);

        String[] genders = {"Male", "Female", "Prefer not to say"};
        spGender.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genders));

        btnRegister.setOnClickListener(v -> {
            String name     = etName.getText().toString().trim();
            String ageStr   = etAge.getText().toString().trim();
            String course   = etCourse.getText().toString().trim();
            String regNo    = etRegNo.getText().toString().trim();
            String email    = etEmail.getText().toString().trim();
            String phone    = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String gender   = spGender.getSelectedItem().toString();

            // Validate required spec fields
            if (name.isEmpty() || regNo.isEmpty() || phone.isEmpty()
                    || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Name, Reg No, Phone, Email, Password are required",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            setLoading(true);

            Map<String, String> params = new HashMap<>();
            params.put("name",       name);
            params.put("reg_number", regNo);
            params.put("phone",      phone);
            params.put("email",      email);
            params.put("password",   password);
            params.put("gender",     gender);
            params.put("course",     course);
            params.put("age",        ageStr.isEmpty() ? "0" : ageStr);

            ApiClient.post(this, ApiConfig.REGISTER, params, new ApiClient.Callback() {
                @Override public void onSuccess(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if ("success".equals(obj.getString("status"))) {
                            setLoading(false);
                            Toast.makeText(RegisterActivity.this,
                                    "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            i.putExtra("role", "student");
                            startActivity(i);
                            finish();
                        } else {
                            setLoading(false);
                            Toast.makeText(RegisterActivity.this,
                                    obj.optString("message", "Registration failed"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) { fallbackRegister(name, ageStr, gender, course, regNo, email, phone, password); }
                }
                @Override public void onError(String error) {
                    // No server – save locally
                    fallbackRegister(name, ageStr, gender, course, regNo, email, phone, password);
                }
            });
        });

        tvLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("role", "student");
            startActivity(i);
        });
    }

    private void fallbackRegister(String name, String ageStr, String gender,
                                   String course, String regNo, String email,
                                   String phone, String password) {
        setLoading(false);
        AppDatabase db = AppDatabase.getInstance(this);
        if (db.studentDao().getByEmail(email) != null) {
            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            return;
        }
        Student s = new Student();
        s.name = name; s.age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
        s.gender = gender; s.course = course; s.regNumber = regNo;
        s.email = email; s.phone = phone; s.password = password;
        s.role = "student"; s.admissionDate = System.currentTimeMillis();
        s.amountOwed = 0; s.roomId = 0;
        long id = db.studentDao().insert(s);
        new SessionManager(this).createSession((int) id, "student", name, email);
        startActivity(new Intent(this, StudentDashboardActivity.class));
        finish();
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!loading);
    }
}
