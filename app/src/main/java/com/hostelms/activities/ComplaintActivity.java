package com.hostelms.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.ComplaintAdapter;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Complaint;
import com.hostelms.utils.SessionManager;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * REFACTORED from original ComplaintActivity.
 *
 * Changes:
 *  - Submits complaint to submit_complaint.php API.
 *  - Falls back to local Room DB insert on API failure (offline support).
 *  - Spec requires only "description" field; subject/category/priority retained as bonus.
 *  - ProgressBar added.
 */
public class ComplaintActivity extends AppCompatActivity {
    private AppDatabase db;
    private SessionManager sm;
    private RecyclerView rvComplaints;
    private Spinner spCategory, spPriority;
    private EditText etSubject, etDescription;
    private ProgressBar progressBar;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db          = AppDatabase.getInstance(this);
        sm          = new SessionManager(this);
        spCategory  = findViewById(R.id.spCategory);
        spPriority  = findViewById(R.id.spPriority);
        etSubject   = findViewById(R.id.etSubject);
        etDescription = findViewById(R.id.etDescription);
        rvComplaints  = findViewById(R.id.rvComplaints);
        progressBar   = findViewById(R.id.progressBar);
        btnSubmit     = findViewById(R.id.btnSubmit);
        rvComplaints.setLayoutManager(new LinearLayoutManager(this));

        spCategory.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Maintenance / Repairs","Plumbing","Electricity",
                        "Noise / Disturbance","Safety / Security","Cleanliness",
                        "Roommate Conflict","Billing / Fees","Staff Conduct","Other"}));
        spPriority.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Low","Normal","High","Urgent"}));
        spPriority.setSelection(1);

        loadComplaints();
        btnSubmit.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String subject = etSubject.getText().toString().trim();
        String desc    = etDescription.getText().toString().trim();
        if (desc.isEmpty()) {
            Toast.makeText(this, "Please describe your issue", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);
        Map<String, String> params = new HashMap<>();
        params.put("student_id",  String.valueOf(sm.getUserId()));
        params.put("description", desc);
        params.put("subject",     subject);
        params.put("category",    spCategory.getSelectedItem().toString());
        params.put("priority",    spPriority.getSelectedItem().toString());

        ApiClient.post(this, ApiConfig.SUBMIT_COMPLAINT, params, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                setLoading(false);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("success".equals(obj.getString("status"))) {
                        clearForm();
                        Toast.makeText(ComplaintActivity.this,
                                "Complaint submitted!", Toast.LENGTH_LONG).show();
                        // Also save locally so user can see it offline
                        saveLocally(subject, desc);
                        loadComplaints();
                    } else {
                        Toast.makeText(ComplaintActivity.this,
                                obj.optString("message","Submit failed"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) { fallbackSave(subject, desc); }
            }
            @Override public void onError(String error) { fallbackSave(subject, desc); }
        });
    }

    private void fallbackSave(String subject, String desc) {
        setLoading(false);
        saveLocally(subject, desc);
        clearForm();
        Toast.makeText(this, "Saved locally. Will sync when online.", Toast.LENGTH_LONG).show();
        loadComplaints();
    }

    private void saveLocally(String subject, String desc) {
        Complaint c = new Complaint();
        c.studentId    = sm.getUserId();
        c.studentName  = sm.getName();
        c.category     = spCategory.getSelectedItem().toString();
        c.priority     = spPriority.getSelectedItem().toString();
        c.subject      = subject.isEmpty() ? "Issue" : subject;
        c.description  = desc;
        c.status       = "Open";
        c.dateSubmitted = System.currentTimeMillis();
        db.complaintDao().insert(c);
    }

    private void clearForm() {
        etSubject.setText(""); etDescription.setText("");
    }

    private void loadComplaints() {
        rvComplaints.setAdapter(new ComplaintAdapter(
                db.complaintDao().getByStudent(sm.getUserId()), false));
    }

    private void setLoading(boolean on) {
        progressBar.setVisibility(on ? View.VISIBLE : View.GONE);
        btnSubmit.setEnabled(!on);
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
