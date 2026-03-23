package com.hostelms.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.AttendanceAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Attendance;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttendanceReportActivity extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView rv;
    private long fromDate = 0, toDate = System.currentTimeMillis();
    private int filterStudentId = -1;
    private Spinner spLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        db = AppDatabase.getInstance(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv = findViewById(R.id.rvAttendance);
        rv.setLayoutManager(new LinearLayoutManager(this));
        spLocation = findViewById(R.id.spLocation);

        String[] locs = {"All Locations","Library","Mess","Gym","AV Room","Hostel"};
        spLocation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locs));

        Button btnFrom = findViewById(R.id.btnFromDate), btnTo = findViewById(R.id.btnToDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        btnFrom.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                Calendar cal = Calendar.getInstance(); cal.set(y, m, d, 0, 0, 0);
                fromDate = cal.getTimeInMillis();
                btnFrom.setText("From: " + sdf.format(new Date(fromDate)));
                loadData();
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnTo.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                Calendar cal = Calendar.getInstance(); cal.set(y, m, d, 23, 59, 59);
                toDate = cal.getTimeInMillis();
                btnTo.setText("To: " + sdf.format(new Date(toDate)));
                loadData();
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        EditText etStudentId = findViewById(R.id.etStudentIdFilter);
        findViewById(R.id.btnFilter).setOnClickListener(v -> {
            String sid = etStudentId.getText().toString().trim();
            filterStudentId = sid.isEmpty() ? -1 : Integer.parseInt(sid);
            loadData();
        });

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> p, android.view.View v, int pos, long id) { loadData(); }
            public void onNothingSelected(AdapterView<?> p) {}
        });

        loadData();
    }

    private void loadData() {
        List<Attendance> list;
        String loc = spLocation.getSelectedItem().toString();
        if (fromDate > 0) {
            list = filterStudentId > 0
                ? db.attendanceDao().filterByStudentAndDate(filterStudentId, fromDate, toDate)
                : db.attendanceDao().getByDateRange(fromDate, toDate);
        } else if (filterStudentId > 0) {
            list = db.attendanceDao().getByStudent(filterStudentId);
        } else if (!"All Locations".equals(loc)) {
            list = db.attendanceDao().getByLocation(loc);
        } else {
            list = db.attendanceDao().getAll();
        }
        if (!"All Locations".equals(loc)) {
            list.removeIf(a -> !a.location.equals(loc));
        }
        ((TextView) findViewById(R.id.tvCount)).setText("Records: " + list.size());
        rv.setAdapter(new AttendanceAdapter(list));
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
