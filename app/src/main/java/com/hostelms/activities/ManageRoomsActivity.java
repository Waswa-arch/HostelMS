package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hostelms.R;
import com.hostelms.adapters.AdminRoomAdapter;
import com.hostelms.database.AppDatabase;

public class ManageRoomsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);
        db = AppDatabase.getInstance(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv = findViewById(R.id.rvRooms);
        rv.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddEditRoomActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        rv.setAdapter(new AdminRoomAdapter(db.roomDao().getAll(), room -> {
            Intent i = new Intent(this, AddEditRoomActivity.class);
            i.putExtra("roomId", room.id);
            startActivity(i);
        }));
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
