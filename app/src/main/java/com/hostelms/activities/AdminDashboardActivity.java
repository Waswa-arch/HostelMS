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
import com.hostelms.utils.SessionManager;

public class AdminDashboardActivity extends AppCompatActivity {
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        sm = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppDatabase db = AppDatabase.getInstance(this);
        ((TextView) findViewById(R.id.tvTotalStudents)).setText(
            String.valueOf(db.studentDao().getAllStudents().size()));
        ((TextView) findViewById(R.id.tvTotalRooms)).setText(
            String.valueOf(db.roomDao().getAll().size()));
        ((TextView) findViewById(R.id.tvOpenComplaints)).setText(
            String.valueOf(db.complaintDao().getAll().stream().filter(c -> "Open".equals(c.status)).count()));
        ((TextView) findViewById(R.id.tvAvailableRooms)).setText(
            String.valueOf(db.roomDao().getAvailable().size()));

        int[] cardIds = {R.id.cardStudents, R.id.cardRooms, R.id.cardAllocate,
                         R.id.cardComplaints, R.id.cardAnnounce, R.id.cardAttendance, R.id.cardQrGen};
        Class<?>[] targets = {ManageStudentsActivity.class, ManageRoomsActivity.class,
                              AllocateRoomActivity.class, ManageComplaintsActivity.class,
                              AddAnnouncementActivity.class, AttendanceReportActivity.class,
                              QrGenerateActivity.class};

        for (int i = 0; i < cardIds.length; i++) {
            final Class<?> t = targets[i];
            CardView card = findViewById(cardIds[i]);
            if (card != null) card.setOnClickListener(v -> startActivity(new Intent(this, t)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sm.logout();
            startActivity(new Intent(this, RoleSelectActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDatabase db = AppDatabase.getInstance(this);
        ((TextView) findViewById(R.id.tvTotalStudents)).setText(
            String.valueOf(db.studentDao().getAllStudents().size()));
        ((TextView) findViewById(R.id.tvOpenComplaints)).setText(
            String.valueOf(db.complaintDao().getAll().stream().filter(c -> "Open".equals(c.status)).count()));
    }
}
