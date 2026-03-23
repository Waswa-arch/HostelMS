package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;
import com.hostelms.utils.SeedData;
import com.hostelms.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SeedData.seedIfEmpty(this);

        new Handler().postDelayed(() -> {
            SessionManager sm = new SessionManager(this);
            Intent intent;
            if (sm.isLoggedIn()) {
                intent = "admin".equals(sm.getRole())
                    ? new Intent(this, AdminDashboardActivity.class)
                    : new Intent(this, StudentDashboardActivity.class);
            } else {
                intent = new Intent(this, RoleSelectActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}
