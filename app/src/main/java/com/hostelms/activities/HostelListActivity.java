package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.HostelAdapter;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.models.Hostel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * NEW – Hostel list screen for Booking and Inquiry modules.
 * Mode is passed via Intent extra "mode" = "booking" | "inquiry".
 *
 * Navigation flow:
 *   Student Dashboard → HostelListActivity → HostelDetailActivity → BookRoomActivity
 *   Student Dashboard → HostelListActivity (inquiry mode – shows contact info)
 */
public class HostelListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ProgressBar progressBar;
    private String mode; // "booking" or "inquiry"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_list);

        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "booking";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("inquiry".equals(mode) ? "Inquiry" : "Select Hostel");
        }

        rv          = findViewById(R.id.rvHostels);
        progressBar = findViewById(R.id.progressBar);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadHostels();
    }

    private void loadHostels() {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.get(this, ApiConfig.GET_HOSTELS, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    List<Hostel> list = new ArrayList<>();
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        Hostel h = new Hostel();
                        h.id              = o.optInt("id");
                        h.name            = o.optString("name");
                        h.imageUrl        = o.optString("image_url");
                        h.personInCharge  = o.optString("person_in_charge");
                        h.contactNumber   = o.optString("contact_number");
                        h.description     = o.optString("description");
                        list.add(h);
                    }
                    setListAdapter(list);
                } catch (Exception e) { fallback(); }
            }
            @Override public void onError(String error) {
                fallback();
            }
        });
    }

    private void fallback() {
        progressBar.setVisibility(View.GONE);
        AppDatabase db = AppDatabase.getInstance(this);
        List<String> names = db.roomDao().getAllHostels();
        List<Hostel> list = new ArrayList<>();
        for (String name : names) {
            Hostel h = new Hostel();
            h.id = 0; // Local fallback
            h.name = name;
            h.description = "Local Database Entry";
            list.add(h);
        }
        if (list.isEmpty()) {
            Toast.makeText(this, "No hostels found locally or on server.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Using local data (offline mode)", Toast.LENGTH_SHORT).show();
            setListAdapter(list);
        }
    }

    private void setListAdapter(List<Hostel> list) {
        rv.setAdapter(new HostelAdapter(list, mode, hostel -> {
            Intent intent = new Intent(HostelListActivity.this, HostelDetailActivity.class);
            intent.putExtra("hostel_id",   hostel.id);
            intent.putExtra("hostel_name", hostel.name);
            intent.putExtra("mode",        mode);
            startActivity(intent);
        }));
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
