package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.RoomAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Booking;
import com.hostelms.database.entities.Room;
import com.hostelms.utils.SessionManager;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private AppDatabase db;
    private SessionManager sm;
    private RecyclerView rvRooms;
    private Spinner spHostel, spMealBundle;
    private Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        db = AppDatabase.getInstance(this);
        sm = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spHostel = findViewById(R.id.spHostel);
        spMealBundle = findViewById(R.id.spMealBundle);
        rvRooms = findViewById(R.id.rvRooms);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));

        List<String> hostels = db.roomDao().getAllHostels();
        hostels.add(0, "All Hostels");
        spHostel.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hostels));
        spHostel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> p, android.view.View v, int pos, long id) { loadRooms(); }
            public void onNothingSelected(AdapterView<?> p) {}
        });

        String[] bundles = {"Room Only (+KES 0)","Bed & Breakfast (+KES 2,500)",
                            "Breakfast & Lunch (+KES 4,500)","Full Board (+KES 7,000)"};
        spMealBundle.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bundles));

        loadRooms();

        findViewById(R.id.btnBook).setOnClickListener(v -> confirmBooking());
    }

    private void loadRooms() {
        String hostel = spHostel.getSelectedItem().toString();
        List<Room> rooms = "All Hostels".equals(hostel)
            ? db.roomDao().getAvailable() : db.roomDao().getByHostel(hostel);
        RoomAdapter adapter = new RoomAdapter(rooms, room -> {
            selectedRoom = room;
            TextView tvSelected = findViewById(R.id.tvSelectedRoom);
            tvSelected.setText("Selected: " + room.hostelName + " Room " + room.roomNumber
                + " (" + room.roomType + ") — KES " + String.format("%.0f", room.pricePerSemester));
        });
        rvRooms.setAdapter(adapter);
    }

    private void confirmBooking() {
        if (selectedRoom == null) {
            Toast.makeText(this, "Please select a room first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedRoom.occupied >= selectedRoom.capacity) {
            Toast.makeText(this, "This room is full", Toast.LENGTH_SHORT).show();
            return;
        }
        String bundle = spMealBundle.getSelectedItem().toString();
        double extra = bundle.contains("7,000") ? 7000 : bundle.contains("4,500") ? 4500 : bundle.contains("2,500") ? 2500 : 0;
        double total = selectedRoom.pricePerSemester + extra;

        Booking b = new Booking();
        b.studentId = sm.getUserId();
        b.roomId = selectedRoom.id;
        b.hostelName = selectedRoom.hostelName;
        b.roomNumber = selectedRoom.roomNumber;
        b.roomType = selectedRoom.roomType;
        b.mealBundle = bundle.split("\\(")[0].trim();
        b.totalPrice = total;
        b.status = "Confirmed";
        b.bookingDate = System.currentTimeMillis();
        b.checkInDone = false;
        db.bookingDao().insert(b);

        // Update student
        com.hostelms.database.entities.Student s = db.studentDao().getById(sm.getUserId());
        s.roomId = selectedRoom.id; s.amountOwed = total;
        db.studentDao().update(s);
        db.roomDao().incrementOccupied(selectedRoom.id);

        Toast.makeText(this, "Booking confirmed! Total: KES " + String.format("%.0f", total), Toast.LENGTH_LONG).show();
        loadRooms();
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
