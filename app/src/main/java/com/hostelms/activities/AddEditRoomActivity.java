package com.hostelms.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Room;

public class AddEditRoomActivity extends AppCompatActivity {
    private AppDatabase db;
    private Room room;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);
        db = AppDatabase.getInstance(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText etHostel = findViewById(R.id.etHostel), etNumber = findViewById(R.id.etRoomNumber),
            etCapacity = findViewById(R.id.etCapacity), etPrice = findViewById(R.id.etPrice),
            etAmenities = findViewById(R.id.etAmenities);
        Spinner spType = findViewById(R.id.spRoomType), spStatus = findViewById(R.id.spStatus), spGender = findViewById(R.id.spGender);

        spType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Single","Double","Quad"}));
        spStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Available","Full","Under Maintenance"}));
        spGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Male","Female","Mixed"}));

        int roomId = getIntent().getIntExtra("roomId", -1);
        if (roomId != -1) {
            isEdit = true;
            room = db.roomDao().getById(roomId);
            if (room != null) {
                etHostel.setText(room.hostelName); etNumber.setText(room.roomNumber);
                etCapacity.setText(String.valueOf(room.capacity)); etPrice.setText(String.valueOf(room.pricePerSemester));
                etAmenities.setText(room.amenities);
                String[] types = {"Single","Double","Quad"};
                for (int i = 0; i < types.length; i++) if (types[i].equals(room.roomType)) { spType.setSelection(i); break; }
            }
            ((Button) findViewById(R.id.btnDelete)).setVisibility(android.view.View.VISIBLE);
            getSupportActionBar().setTitle("Edit Room");
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (room == null) room = new Room();
            room.hostelName = etHostel.getText().toString().trim();
            room.roomNumber = etNumber.getText().toString().trim();
            room.roomType = spType.getSelectedItem().toString();
            room.status = spStatus.getSelectedItem().toString();
            room.gender = spGender.getSelectedItem().toString();
            room.amenities = etAmenities.getText().toString().trim();
            try { room.capacity = Integer.parseInt(etCapacity.getText().toString()); } catch(Exception e) { room.capacity = 1; }
            try { room.pricePerSemester = Double.parseDouble(etPrice.getText().toString()); } catch(Exception e) { room.pricePerSemester = 0; }
            if (!isEdit) { room.occupied = 0; db.roomDao().insert(room); }
            else db.roomDao().update(room);
            Toast.makeText(this, isEdit ? "Room updated" : "Room added", Toast.LENGTH_SHORT).show();
            finish();
        });
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (room != null) { db.roomDao().delete(room); finish(); }
        });
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
