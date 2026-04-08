package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;

public class RegisterActivity extends AppCompatActivity {

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
        Button   btnRegister = findViewById(R.id.btnRegister);
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

            // Validation
            if (name.isEmpty() || course.isEmpty() || regNo.isEmpty()
                    || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields (*)",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(this);

            // Check for duplicate email
            if (db.studentDao().getByEmail(email) != null) {
                Toast.makeText(this, "An account with this email already exists",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Check for duplicate reg number
            if (db.studentDao().getByRegNumber(regNo) != null) {
                Toast.makeText(this, "Registration number already registered",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Create student record
            Student s = new Student();
            s.name          = name;
            s.age           = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
            s.gender        = spGender.getSelectedItem().toString();
            s.course        = course;
            s.regNumber     = regNo;
            s.email         = email;
            s.phone         = phone;
            s.password      = password;
            s.role          = "student";
            s.admissionDate = System.currentTimeMillis();
            s.amountOwed    = 0;
            s.roomId        = 0;

            db.studentDao().insert(s);

            // Show success message
            Toast.makeText(this,
                    "Account created successfully! Please log in.",
                    Toast.LENGTH_LONG).show();

            // Redirect to LOGIN page — do NOT auto-login
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("role", "student");
            // Pass the email so login field is pre-filled
            intent.putExtra("prefillEmail", email);
            // Clear back stack so user cannot go back to register
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        tvLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("role", "student");
            startActivity(i);
        });
    }
}