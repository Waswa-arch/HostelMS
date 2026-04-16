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
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * REFACTORED from original AnnouncementsActivity.
 */
public class AnnouncementsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv          = findViewById(R.id.rvAnnouncements);
        progressBar = findViewById(R.id.progressBar);
        rv.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        ApiClient.get(this, ApiConfig.GET_ANNOUNCEMENTS, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    List<Announcement> list = new ArrayList<>();
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        Announcement a = new Announcement();
                        a.id         = o.optInt("id");
                        a.title      = o.optString("title");
                        a.body       = o.optString("message");
                        a.author     = o.optString("admin_name", "Admin");
                        a.isUrgent   = o.optInt("is_urgent", 0) == 1;
                        a.datePosted = o.optLong("created_at_ts",
                                System.currentTimeMillis());
                        list.add(a);
                    }
                    rv.setAdapter(new AnnouncementAdapter(list));
                } catch (Exception e) { loadFromLocal(); }
            }
            @Override public void onError(String error) { loadFromLocal(); }
        });
    }

    private void loadFromLocal() {
        progressBar.setVisibility(View.GONE);
        rv.setAdapter(new AnnouncementAdapter(
                AppDatabase.getInstance(this).announcementDao().getAll()));
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
