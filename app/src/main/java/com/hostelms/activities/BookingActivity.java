package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hostelms.R;
import com.hostelms.adapters.HostelAdapter;
import com.hostelms.adapters.RoomAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Booking;
import com.hostelms.database.entities.Room;
import com.hostelms.database.entities.Student;
import com.hostelms.models.Hostel;
import com.hostelms.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private AppDatabase db;
    private SessionManager sm;

    // Step 1 views
    private RecyclerView rvHostels;
    private LinearLayout layoutStep1;

    // Step 2 views
    private LinearLayout layoutStep2;
    private RecyclerView rvRooms;
    private Spinner spMealBundle;
    private TextView tvStep2Title, tvSelectedRoom;
    private Button btnBook, btnBackToHostels;

    private String selectedHostelName;
    private Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = AppDatabase.getInstance(this);
        sm = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Book a Room");
        }

        // Step 1 - hostel list
        layoutStep1   = findViewById(R.id.layoutStep1);
        rvHostels     = findViewById(R.id.rvHostels);
        rvHostels.setLayoutManager(new LinearLayoutManager(this));

        // Step 2 - room picker
        layoutStep2     = findViewById(R.id.layoutStep2);
        rvRooms         = findViewById(R.id.rvRooms);
        spMealBundle    = findViewById(R.id.spMealBundle);
        tvStep2Title    = findViewById(R.id.tvStep2Title);
        tvSelectedRoom  = findViewById(R.id.tvSelectedRoom);
        btnBook         = findViewById(R.id.btnBook);
        btnBackToHostels = findViewById(R.id.btnBackToHostels);

        rvRooms.setLayoutManager(new LinearLayoutManager(this));

        String[] bundles = {
                "Room Only (+KES 0)",
                "Bed & Breakfast (+KES 2,500)",
                "Breakfast & Lunch (+KES 4,500)",
                "Full Board (+KES 7,000)"
        };
        spMealBundle.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, bundles));

        loadHostelList();

        btnBackToHostels.setOnClickListener(v -> showStep1());
        btnBook.setOnClickListener(v -> confirmBooking());
    }

    // ── STEP 1: show hostel cards ─────────────────────────────────────────────
    private void loadHostelList() {
        List<String> names = db.roomDao().getAllHostels();
        List<Hostel> hostels = new ArrayList<>();
        for (String name : names) {
            Hostel h = new Hostel();
            h.name = name;
            hostels.add(h);
        }

        HostelAdapter adapter = new HostelAdapter(hostels, "booking", hostel -> {
            selectedHostelName = hostel.name;
            showStep2(hostel.name);
        });
        rvHostels.setAdapter(adapter);
        showStep1();
    }

    private void showStep1() {
        layoutStep1.setVisibility(android.view.View.VISIBLE);
        layoutStep2.setVisibility(android.view.View.GONE);
        selectedRoom = null;
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Choose a Hostel");
    }

    // ── STEP 2: show rooms for chosen hostel ──────────────────────────────────
    private void showStep2(String hostelName) {
        layoutStep1.setVisibility(android.view.View.GONE);
        layoutStep2.setVisibility(android.view.View.VISIBLE);
        tvStep2Title.setText(hostelName + " — Select Room");
        tvSelectedRoom.setText("Tap a room below to select it");
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(hostelName);

        List<Room> rooms = db.roomDao().getByHostel(hostelName);
        RoomAdapter adapter = new RoomAdapter(rooms, room -> {
            selectedRoom = room;
            tvSelectedRoom.setText(
                    "✔  Room " + room.roomNumber + " — " + room.roomType
                            + "  |  KES " + String.format("%,.0f", room.pricePerSemester) + "/sem"
            );
        });
        rvRooms.setAdapter(adapter);
    }

    // ── CONFIRM BOOKING ───────────────────────────────────────────────────────
    private void confirmBooking() {
        if (selectedRoom == null) {
            Toast.makeText(this, "Please select a room first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedRoom.occupied >= selectedRoom.capacity) {
            Toast.makeText(this, "This room is full. Please choose another.", Toast.LENGTH_SHORT).show();
            return;
        }

        String bundle = spMealBundle.getSelectedItem().toString();
        double extra  = bundle.contains("7,000") ? 7000
                : bundle.contains("4,500") ? 4500
                : bundle.contains("2,500") ? 2500 : 0;
        double total  = selectedRoom.pricePerSemester + extra;

        Booking b = new Booking();
        b.studentId   = sm.getUserId();
        b.roomId      = selectedRoom.id;
        b.hostelName  = selectedRoom.hostelName;
        b.roomNumber  = selectedRoom.roomNumber;
        b.roomType    = selectedRoom.roomType;
        b.mealBundle  = bundle.split("\\(")[0].trim();
        b.totalPrice  = total;
        b.status      = "Confirmed";
        b.bookingDate = System.currentTimeMillis();
        b.checkInDone = false;
        db.bookingDao().insert(b);

        // Update student record
        Student s = db.studentDao().getById(sm.getUserId());
        if (s != null) {
            s.roomId     = selectedRoom.id;
            s.amountOwed = total;
            db.studentDao().update(s);
        }
        db.roomDao().incrementOccupied(selectedRoom.id);

        Toast.makeText(this,
                "✔ Booking confirmed!\n" + selectedRoom.hostelName
                        + " Room " + selectedRoom.roomNumber
                        + "\nTotal: KES " + String.format("%,.0f", total),
                Toast.LENGTH_LONG).show();

        // Go back to hostel list and refresh
        showStep1();
        loadHostelList();
    }

    @Override
    public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
