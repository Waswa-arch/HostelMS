package com.hostelms.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.ComplaintAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Complaint;
import java.util.List;

public class ManageComplaintsActivity extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_complaints);
        db = AppDatabase.getInstance(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv = findViewById(R.id.rvComplaints);
        rv.setLayoutManager(new LinearLayoutManager(this));
        load();
    }

    private void load() {
        List<Complaint> list = db.complaintDao().getAll();
        rv.setAdapter(new ComplaintAdapter(list, true, complaint -> {
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this);
            b.setTitle("Update: " + complaint.subject);
            String[] statuses = {"Open","In Progress","Resolved"};
            EditText et = new EditText(this); et.setHint("Response (optional)"); et.setText(complaint.adminResponse);
            LinearLayout ll = new LinearLayout(this); ll.setOrientation(LinearLayout.VERTICAL);
            Spinner sp = new Spinner(this);
            sp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuses));
            for (int i = 0; i < statuses.length; i++) if (statuses[i].equals(complaint.status)) { sp.setSelection(i); break; }
            ll.setPadding(32,16,32,0); ll.addView(sp); ll.addView(et);
            b.setView(ll);
            b.setPositiveButton("Update", (d, w) -> {
                complaint.status = sp.getSelectedItem().toString();
                complaint.adminResponse = et.getText().toString().trim();
                db.complaintDao().update(complaint); load();
            });
            b.setNegativeButton("Cancel", null);
            b.show();
        }));
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
