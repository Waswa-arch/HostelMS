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
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Announcement;
import com.hostelms.utils.SessionManager;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * REFACTORED from original AddAnnouncementActivity.
 *
 * Changes:
 *  - Posts to add_announcement.php API.
 *  - Falls back to local Room DB on failure.
 *  - ProgressBar added.
 */
public class AddAnnouncementActivity extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private Button btnPost;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        db = AppDatabase.getInstance(this);
        sm = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText etTitle  = findViewById(R.id.etTitle);
        EditText etBody   = findViewById(R.id.etBody);
        EditText etAuthor = findViewById(R.id.etAuthor);
        CheckBox cbUrgent = findViewById(R.id.cbUrgent);
        rv          = findViewById(R.id.rvAnnouncements);
        progressBar = findViewById(R.id.progressBar);
        btnPost     = findViewById(R.id.btnPost);
        rv.setLayoutManager(new LinearLayoutManager(this));
        etAuthor.setText(sm.getName());
        loadList();

        btnPost.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String body  = etBody.getText().toString().trim();
            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Title and body required", Toast.LENGTH_SHORT).show();
                return;
            }
            setLoading(true);

            Map<String, String> params = new HashMap<>();
            params.put("admin_id", String.valueOf(sm.getUserId()));
            params.put("title",    title);
            params.put("message",  body);
            params.put("is_urgent", cbUrgent.isChecked() ? "1" : "0");

            ApiClient.post(this, ApiConfig.ADD_ANNOUNCEMENT, params, new ApiClient.Callback() {
                @Override public void onSuccess(String response) {
                    setLoading(false);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if ("success".equals(obj.getString("status"))) {
                            saveLocalAnnouncement(title, body, etAuthor.getText().toString().trim(),
                                    cbUrgent.isChecked());
                            etTitle.setText(""); etBody.setText(""); cbUrgent.setChecked(false);
                            Toast.makeText(AddAnnouncementActivity.this,
                                    "Announcement posted", Toast.LENGTH_SHORT).show();
                            loadList();
                        }
                    } catch (Exception e) { fallbackLocal(title, body, etAuthor.getText().toString(), cbUrgent.isChecked()); }
                }
                @Override public void onError(String error) {
                    fallbackLocal(title, body, etAuthor.getText().toString(), cbUrgent.isChecked());
                }
            });
        });
    }

    private void fallbackLocal(String title, String body, String author, boolean urgent) {
        setLoading(false);
        saveLocalAnnouncement(title, body, author, urgent);
        Toast.makeText(this, "Saved locally", Toast.LENGTH_SHORT).show();
        loadList();
    }

    private void saveLocalAnnouncement(String title, String body, String author, boolean urgent) {
        Announcement a = new Announcement();
        a.title = title; a.body = body; a.author = author;
        a.isUrgent = urgent; a.datePosted = System.currentTimeMillis();
        db.announcementDao().insert(a);
    }

    private void loadList() {
        rv.setAdapter(new AnnouncementAdapter(db.announcementDao().getAll()));
    }

    private void setLoading(boolean on) {
        progressBar.setVisibility(on ? View.VISIBLE : View.GONE);
        btnPost.setEnabled(!on);
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
