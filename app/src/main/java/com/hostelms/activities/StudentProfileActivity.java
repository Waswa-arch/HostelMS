package com.hostelms.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.AnnouncementAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;
import com.hostelms.utils.DateUtils;
import com.hostelms.utils.SessionManager;
import java.util.List;
import java.util.Locale;

public class StudentProfileActivity extends AppCompatActivity {
    private AppDatabase db;
    private Student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionManager sm = new SessionManager(this);
        db = AppDatabase.getInstance(this);
        s = db.studentDao().getById(sm.getUserId());
        if (s == null) { finish(); return; }

        setupProfile();
        setupTabs();
        loadNotices();
    }

    private void setupProfile() {
        ((TextView) findViewById(R.id.tvName)).setText(s.name);
        ((TextView) findViewById(R.id.tvEmail)).setText(s.email);
        ((TextView) findViewById(R.id.tvRegNo)).setText(s.regNumber);
        ((TextView) findViewById(R.id.tvCourse)).setText(s.course);
        ((TextView) findViewById(R.id.tvGender)).setText(s.gender);
        ((TextView) findViewById(R.id.tvAge)).setText(String.valueOf(s.age));
        ((TextView) findViewById(R.id.tvPhone)).setText(s.phone != null ? s.phone : "—");
        ((TextView) findViewById(R.id.tvAdmission)).setText(DateUtils.formatDate(s.admissionDate));

        View cardHostel = findViewById(R.id.cardHostelAssigned);
        TextView tvHostelDetail = findViewById(R.id.tvHostelDetail);
        
        String hName = s.hostelName;
        String rNum = s.roomNumber;
        
        if (s.roomId > 0) {
            Room r = db.roomDao().getById(s.roomId);
            if (r != null) {
                hName = r.hostelName;
                rNum = r.roomNumber;
            }
        }

        if (hName != null && !hName.isEmpty()) {
            if (cardHostel != null) cardHostel.setVisibility(View.VISIBLE);
            if (tvHostelDetail != null) {
                tvHostelDetail.setText(String.format(Locale.getDefault(), "%s — Room %s (Bed %d)", hName, rNum, s.bedNumber));
            }
        } else {
            if (cardHostel != null) cardHostel.setVisibility(View.GONE);
        }

        TextView tvOwed = findViewById(R.id.tvOwed);
        if (tvOwed != null) {
            tvOwed.setText(String.format(Locale.getDefault(), "KES %.0f", s.amountOwed));
            tvOwed.setTextColor(s.amountOwed > 0
                ? getColor(R.color.colorRed) : getColor(R.color.colorGreen));
        }

        Button btnEdit = findViewById(R.id.btnEditProfile);
        EditText etPhone = findViewById(R.id.etEditPhone);
        EditText etPassword = findViewById(R.id.etEditPassword);
        if (etPhone != null) etPhone.setText(s.phone);

        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                if (etPhone != null && etPassword != null) {
                    String newPhone = etPhone.getText().toString().trim();
                    String newPass = etPassword.getText().toString().trim();
                    if (!newPhone.isEmpty()) {
                        s.phone = newPhone;
                        ((TextView) findViewById(R.id.tvPhone)).setText(newPhone);
                    }
                    if (!newPass.isEmpty()) s.password = newPass;
                    db.studentDao().update(s);
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupTabs() {
        View profileContent = findViewById(R.id.profileContent);
        View noticesContent = findViewById(R.id.noticesContent);
        View tabProfile = findViewById(R.id.tabProfile);
        View tabNotices = findViewById(R.id.tabNotices);
        TextView tvTabProfile = findViewById(R.id.tvTabProfile);
        TextView tvTabNotices = findViewById(R.id.tvTabNotices);

        if (tabProfile != null) {
            tabProfile.setOnClickListener(v -> {
                if (profileContent != null) profileContent.setVisibility(View.VISIBLE);
                if (noticesContent != null) noticesContent.setVisibility(View.GONE);
                if (tvTabProfile != null) tvTabProfile.setTextColor(getColor(R.color.colorAccent));
                if (tvTabNotices != null) tvTabNotices.setTextColor(getColor(R.color.colorMuted));
            });
        }

        if (tabNotices != null) {
            tabNotices.setOnClickListener(v -> {
                if (profileContent != null) profileContent.setVisibility(View.GONE);
                if (noticesContent != null) noticesContent.setVisibility(View.VISIBLE);
                if (tvTabProfile != null) tvTabProfile.setTextColor(getColor(R.color.colorMuted));
                if (tvTabNotices != null) tvTabNotices.setTextColor(getColor(R.color.colorAccent));
            });
        }
    }

    private void loadNotices() {
        RecyclerView rv = findViewById(R.id.rvNotices);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));
            List<Announcement> list = db.announcementDao().getAll();
            View tvNoNotices = findViewById(R.id.tvNoNotices);
            if (list.isEmpty()) {
                if (tvNoNotices != null) tvNoNotices.setVisibility(View.VISIBLE);
            } else {
                if (tvNoNotices != null) tvNoNotices.setVisibility(View.GONE);
                rv.setAdapter(new AnnouncementAdapter(list));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
