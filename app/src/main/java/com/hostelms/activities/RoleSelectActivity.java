package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;

public class RoleSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        Button btnStudent = findViewById(R.id.btnStudent);
        Button btnAdmin = findViewById(R.id.btnAdmin);

        btnStudent.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("role", "student");
            startActivity(i);
        });
        btnAdmin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("role", "admin");
            startActivity(i);
        });
    }
}
