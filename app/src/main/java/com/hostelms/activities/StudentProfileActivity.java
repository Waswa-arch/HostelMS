package com.hostelms.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;
import com.hostelms.utils.DateUtils;
import com.hostelms.utils.SessionManager;
import java.util.List;

public class StudentProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionManager sm = new SessionManager(this);
        AppDatabase db = AppDatabase.getInstance(this);
        Student s = db.studentDao().getById(sm.getUserId());
        if (s == null) { finish(); return; }

        ((TextView) findViewById(R.id.tvName)).setText(s.name);
        ((TextView) findViewById(R.id.tvEmail)).setText(s.email);
        ((TextView) findViewById(R.id.tvRegNo)).setText(s.regNumber);
        ((TextView) findViewById(R.id.tvCourse)).setText(s.course);
        ((TextView) findViewById(R.id.tvGender)).setText(s.gender);
        ((TextView) findViewById(R.id.tvAge)).setText(String.valueOf(s.age));
        ((TextView) findViewById(R.id.tvPhone)).setText(s.phone != null ? s.phone : "—");
        ((TextView) findViewById(R.id.tvAdmission)).setText(DateUtils.formatDate(s.admissionDate));

        String hostelInfo = "Not yet assigned";
        if (s.roomId > 0) {
            Room r = db.roomDao().getById(s.roomId);
            if (r != null) hostelInfo = r.hostelName + " — Room " + r.roomNumber + " (Bed " + s.bedNumber + ")";
        }
        ((TextView) findViewById(R.id.tvRoom)).setText(hostelInfo);
        TextView tvOwed = findViewById(R.id.tvOwed);
        tvOwed.setText(String.format("KES %.0f", s.amountOwed));
        tvOwed.setTextColor(s.amountOwed > 0
            ? getColor(R.color.colorRed) : getColor(R.color.colorGreen));

        // Edit basic info
        Button btnEdit = findViewById(R.id.btnEditProfile);
        EditText etPhone = findViewById(R.id.etEditPhone);
        EditText etPassword = findViewById(R.id.etEditPassword);
        etPhone.setText(s.phone);

        btnEdit.setOnClickListener(v -> {
            String newPhone = etPhone.getText().toString().trim();
            String newPass = etPassword.getText().toString().trim();
            if (!newPhone.isEmpty()) s.phone = newPhone;
            if (!newPass.isEmpty()) s.password = newPass;
            db.studentDao().update(s);
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
