package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;
import com.hostelms.utils.SeedData;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Seed local database with demo data if empty
        SeedData.seedIfEmpty(this);

        // Always go to RoleSelect — never skip login
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, RoleSelectActivity.class));
            finish();
        }, 1800);
    }
}
