package com.hostelms.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;

public class AddEditStudentActivity extends AppCompatActivity {
    private AppDatabase db;
    private Student student;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_student);
        db = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText etName = findViewById(R.id.etName), etAge = findViewById(R.id.etAge),
            etCourse = findViewById(R.id.etCourse), etRegNo = findViewById(R.id.etRegNo),
            etEmail = findViewById(R.id.etEmail), etPhone = findViewById(R.id.etPhone),
            etPassword = findViewById(R.id.etPassword), etOwed = findViewById(R.id.etAmountOwed);
        Spinner spGender = findViewById(R.id.spGender);

        String[] genders = {"Male","Female","Prefer not to say"};
        spGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders));

        int studentId = getIntent().getIntExtra("studentId", -1);
        if (studentId != -1) {
            isEdit = true;
            student = db.studentDao().getById(studentId);
            if (student != null) {
                etName.setText(student.name); etAge.setText(String.valueOf(student.age));
                etCourse.setText(student.course); etRegNo.setText(student.regNumber);
                etEmail.setText(student.email); etPhone.setText(student.phone);
                etOwed.setText(String.valueOf(student.amountOwed));
                for (int i = 0; i < genders.length; i++)
                    if (genders[i].equals(student.gender)) { spGender.setSelection(i); break; }
            }
            ((Button) findViewById(R.id.btnDelete)).setVisibility(android.view.View.VISIBLE);
            getSupportActionBar().setTitle("Edit Student");
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (student == null) student = new Student();
            student.name = etName.getText().toString().trim();
            student.age = etAge.getText().toString().isEmpty() ? 0 : Integer.parseInt(etAge.getText().toString().trim());
            student.gender = spGender.getSelectedItem().toString();
            student.course = etCourse.getText().toString().trim();
            student.regNumber = etRegNo.getText().toString().trim();
            student.email = etEmail.getText().toString().trim();
            student.phone = etPhone.getText().toString().trim();
            if (!etPassword.getText().toString().isEmpty()) student.password = etPassword.getText().toString().trim();
            student.amountOwed = etOwed.getText().toString().isEmpty() ? 0 : Double.parseDouble(etOwed.getText().toString());
            if (!isEdit) { student.role = "student"; student.admissionDate = System.currentTimeMillis(); db.studentDao().insert(student); }
            else db.studentDao().update(student);
            Toast.makeText(this, isEdit ? "Student updated" : "Student added", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (student != null) { db.studentDao().delete(student); finish(); }
        });
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
