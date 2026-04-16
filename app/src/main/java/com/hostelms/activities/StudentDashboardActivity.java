package com.hostelms.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;
import com.hostelms.utils.SessionManager;

/**
 * REFACTORED from original StudentDashboardActivity.
 */
public class StudentDashboardActivity extends AppCompatActivity {
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        sm = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvRegNo   = findViewById(R.id.tvRegNo);
        Student student = AppDatabase.getInstance(this).studentDao().getById(sm.getUserId());
        if (student != null) {
            tvWelcome.setText("Hello, " + student.name.split(" ")[0] + " 👋");
            tvRegNo.setText(student.regNumber);
        }

        CardView cardBooking = findViewById(R.id.cardBooking);
        if (cardBooking != null) cardBooking.setOnClickListener(v -> {
            Intent i = new Intent(this, HostelListActivity.class);
            i.putExtra("mode", "booking");
            startActivity(i);
        });

        CardView cardInquiry = findViewById(R.id.cardInquiry);
        if (cardInquiry != null) cardInquiry.setOnClickListener(v -> {
            Intent i = new Intent(this, HostelListActivity.class);
            i.putExtra("mode", "inquiry");
            startActivity(i);
        });

        CardView cardComplaints = findViewById(R.id.cardComplaints);
        if (cardComplaints != null) cardComplaints.setOnClickListener(v ->
                startActivity(new Intent(this, ComplaintActivity.class)));

        CardView cardAnnouncements = findViewById(R.id.cardAnnouncements);
        if (cardAnnouncements != null) cardAnnouncements.setOnClickListener(v ->
                startActivity(new Intent(this, AnnouncementsActivity.class)));

        CardView cardFaq = findViewById(R.id.cardFaq);
        if (cardFaq != null) cardFaq.setOnClickListener(v ->
                startActivity(new Intent(this, FaqActivity.class)));

        CardView cardHowToUse = findViewById(R.id.cardHowToUse);
        if (cardHowToUse != null) cardHowToUse.setOnClickListener(v ->
                startActivity(new Intent(this, HowToUseActivity.class)));

        CardView cardCheckin = findViewById(R.id.cardCheckin);
        if (cardCheckin != null) cardCheckin.setOnClickListener(v ->
                startActivity(new Intent(this, CheckInActivity.class)));

        CardView cardQrScan = findViewById(R.id.cardQrScan);
        if (cardQrScan != null) cardQrScan.setOnClickListener(v ->
                startActivity(new Intent(this, QrScanActivity.class)));

        CardView cardProfile = findViewById(R.id.cardProfile);
        if (cardProfile != null) cardProfile.setOnClickListener(v ->
                startActivity(new Intent(this, StudentProfileActivity.class)));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu); return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sm.logout();
            startActivity(new Intent(this, RoleSelectActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
