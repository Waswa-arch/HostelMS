package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;
import com.hostelms.utils.SessionManager;

/**
 * REFACTORED from original StudentDashboardActivity.
 *
 * Changes made:
 *  - Booking card now goes to HostelListActivity (API-driven hostel list).
 *  - Added Inquiry card → HostelListActivity (mode=inquiry).
 *  - Added FAQ and HowToUse cards.
 *  - Retained: CheckIn, Complaints, Announcements, QR Scan, Profile.
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

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvRegNo   = findViewById(R.id.tvRegNo);
        Student student = AppDatabase.getInstance(this).studentDao().getById(sm.getUserId());
        if (student != null) {
            tvWelcome.setText("Hello, " + student.name.split(" ")[0] + " 👋");
            tvRegNo.setText(student.regNumber);
        }

        // ── Card → Activity mapping ──────────────────────────────────────
        // Booking: goes to API hostel list
        CardView cardBooking = findViewById(R.id.cardBooking);
        if (cardBooking != null) cardBooking.setOnClickListener(v -> {
            Intent i = new Intent(this, HostelListActivity.class);
            i.putExtra("mode", "booking");
            startActivity(i);
        });

        // Inquiry: goes to hostel list in inquiry mode
        CardView cardInquiry = findViewById(R.id.cardInquiry);
        if (cardInquiry != null) cardInquiry.setOnClickListener(v -> {
            Intent i = new Intent(this, HostelListActivity.class);
            i.putExtra("mode", "inquiry");
            startActivity(i);
        });

        // Complaints
        CardView cardComplaints = findViewById(R.id.cardComplaints);
        if (cardComplaints != null) cardComplaints.setOnClickListener(v ->
                startActivity(new Intent(this, ComplaintActivity.class)));

        // Announcements
        CardView cardAnnouncements = findViewById(R.id.cardAnnouncements);
        if (cardAnnouncements != null) cardAnnouncements.setOnClickListener(v ->
                startActivity(new Intent(this, AnnouncementsActivity.class)));

        // FAQ
        CardView cardFaq = findViewById(R.id.cardFaq);
        if (cardFaq != null) cardFaq.setOnClickListener(v ->
                startActivity(new Intent(this, FaqActivity.class)));

        // How to Use
        CardView cardHowToUse = findViewById(R.id.cardHowToUse);
        if (cardHowToUse != null) cardHowToUse.setOnClickListener(v ->
                startActivity(new Intent(this, HowToUseActivity.class)));

        // Retained original cards
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
