package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hostelms.R;
import com.hostelms.adapters.RoomApiAdapter;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Room;
import com.hostelms.models.HostelRoom;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * NEW – Hostel detail screen.
 */
public class HostelDetailActivity extends AppCompatActivity {
    private int hostelId;
    private String hostelName, mode;
    private ProgressBar progressBar;
    private RecyclerView rvRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        hostelId   = getIntent().getIntExtra("hostel_id", 0);
        hostelName = getIntent().getStringExtra("hostel_name");
        mode       = getIntent().getStringExtra("mode");
        if (mode == null) mode = "booking";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(hostelName);
        }

        progressBar = findViewById(R.id.progressBar);
        rvRooms = findViewById(R.id.rvRooms);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        if (hostelId == 0) {
            fallback();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        ApiClient.get(this, ApiConfig.GET_HOSTEL_DETAIL + "?hostel_id=" + hostelId,
                new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject hostel = json.getJSONObject("hostel");

                    ((TextView) findViewById(R.id.tvHostelName)).setText(hostel.optString("name"));
                    ((TextView) findViewById(R.id.tvDescription)).setText(hostel.optString("description"));
                    ((TextView) findViewById(R.id.tvPersonInCharge))
                            .setText("In charge: " + hostel.optString("person_in_charge"));
                    ((TextView) findViewById(R.id.tvContact))
                            .setText("Contact: " + hostel.optString("contact_number"));

                    Glide.with(HostelDetailActivity.this)
                            .load(hostel.optString("image_url"))
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into((ImageView) findViewById(R.id.ivHostelImage));

                    List<HostelRoom> rooms = new ArrayList<>();
                    JSONArray arr = json.getJSONArray("rooms");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        HostelRoom r = new HostelRoom();
                        r.id         = o.optInt("id");
                        r.number     = o.optString("room_number");
                        r.type       = o.optString("type");
                        r.price      = o.optString("price");
                        r.amenities  = o.optString("amenities");
                        r.status     = o.optString("status");
                        rooms.add(r);
                    }
                    setRoomAdapter(rooms);
                } catch (Exception e) { fallback(); }
            }
            @Override public void onError(String error) {
                fallback();
            }
        });
    }

    private void fallback() {
        progressBar.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tvHostelName)).setText(hostelName);
        ((TextView) findViewById(R.id.tvDescription)).setText("Offline Mode - Local Data");
        
        AppDatabase db = AppDatabase.getInstance(this);
        List<Room> localRooms = db.roomDao().getByHostel(hostelName);
        List<HostelRoom> rooms = new ArrayList<>();
        for (Room lr : localRooms) {
            HostelRoom r = new HostelRoom();
            r.id = lr.id;
            r.number = lr.roomNumber;
            r.type = lr.roomType;
            r.price = String.valueOf(lr.pricePerSemester);
            r.amenities = lr.amenities;
            r.status = lr.occupied < lr.capacity ? "available" : "full";
            rooms.add(r);
        }
        setRoomAdapter(rooms);
        if (hostelId != 0) Toast.makeText(this, "Server unreachable. Using local data.", Toast.LENGTH_SHORT).show();
    }

    private void setRoomAdapter(List<HostelRoom> rooms) {
        if ("booking".equals(mode)) {
            rvRooms.setAdapter(new RoomApiAdapter(rooms, room -> {
                if (!"available".equals(room.status)) {
                    Toast.makeText(HostelDetailActivity.this,
                            "Room not available", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HostelDetailActivity.this,
                        BookRoomActivity.class);
                intent.putExtra("room_id",     room.id);
                intent.putExtra("hostel_id",   hostelId);
                intent.putExtra("hostel_name", hostelName);
                intent.putExtra("room_number", room.number);
                intent.putExtra("price",       room.price);
                startActivity(intent);
            }));
        } else {
            rvRooms.setAdapter(new RoomApiAdapter(rooms, null));
        }
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
