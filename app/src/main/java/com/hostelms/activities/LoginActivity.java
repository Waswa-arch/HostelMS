package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;
import com.hostelms.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        role = getIntent().getStringExtra("role");

        TextView tvTitle    = findViewById(R.id.tvLoginTitle);
        EditText etEmail    = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button   btnLogin   = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Set title based on role
        tvTitle.setText("admin".equals(role) ? "Admin Login" : "Student Login");

        // Pre-fill email if coming from registration
        String prefillEmail = getIntent().getStringExtra("prefillEmail");
        if (prefillEmail != null && !prefillEmail.isEmpty()) {
            etEmail.setText(prefillEmail);
            // Move cursor to password field
            etPassword.requestFocus();
        }

        // Only show register link for student portal
        tvRegister.setVisibility("student".equals(role)
                ? android.view.View.VISIBLE : android.view.View.GONE);

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass  = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter your email and password",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(this);
            Student s = db.studentDao().login(email, pass);

            if (s == null) {
                Toast.makeText(this, "Incorrect email or password",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!s.role.equals(role)) {
                Toast.makeText(this,
                        "This account does not belong to the " + role + " portal",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Create session
            new SessionManager(this).createSession(s.id, s.role, s.name, s.email);

            // Navigate to correct dashboard
            Intent intent = new Intent(this,
                    "admin".equals(role)
                            ? AdminDashboardActivity.class
                            : StudentDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
