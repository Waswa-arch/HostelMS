package com.hostelms.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Booking;
import com.hostelms.database.entities.Room;
import com.hostelms.utils.SessionManager;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * UPDATED from previous refactor.
 */
public class BookRoomActivity extends AppCompatActivity {
    private String checkInDate = "", checkOutDate = "";
    private int roomId, hostelId;
    private String hostelName, roomNumber, price;
    private ProgressBar progressBar;
    private Button btnBook, btnCheckIn, btnCheckOut;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        sm = new SessionManager(this);

        roomId     = getIntent().getIntExtra("room_id", 0);
        hostelId   = getIntent().getIntExtra("hostel_id", 0);
        hostelName = getIntent().getStringExtra("hostel_name");
        roomNumber = getIntent().getStringExtra("room_number");
        price      = getIntent().getStringExtra("price");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Confirm Booking");
        }

        ((TextView) findViewById(R.id.tvHostelName)).setText(hostelName);
        ((TextView) findViewById(R.id.tvRoomNumber)).setText("Room " + roomNumber);
        ((TextView) findViewById(R.id.tvPrice)).setText("Price: " + price + "/month");

        btnCheckIn  = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnBook     = findViewById(R.id.btnBook);
        progressBar = findViewById(R.id.progressBar);

        Spinner spMeal = findViewById(R.id.spMealBundle);
        String[] bundles = {"Room Only", "Bed & Breakfast (+RM 250)",
                            "Breakfast & Lunch (+RM 450)", "Full Board (+RM 700)"};
        spMeal.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, bundles));

        btnCheckIn.setOnClickListener(v  -> pickDate(true));
        btnCheckOut.setOnClickListener(v -> pickDate(false));
        btnBook.setOnClickListener(v     -> confirmBooking());
    }

    private void pickDate(boolean isCheckIn) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            String date = y + "-" + String.format("%02d", m + 1) + "-" + String.format("%02d", d);
            if (isCheckIn) { checkInDate = date; btnCheckIn.setText("Check-in: " + date); }
            else           { checkOutDate = date; btnCheckOut.setText("Check-out: " + date); }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void confirmBooking() {
        if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            Toast.makeText(this, "Please select check-in and check-out dates",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        setLoading(true);

        Map<String, String> params = new HashMap<>();
        params.put("student_id", String.valueOf(sm.getUserId()));
        params.put("room_id",    String.valueOf(roomId));
        params.put("hostel_id",  String.valueOf(hostelId));
        params.put("check_in",   checkInDate);
        params.put("check_out",  checkOutDate);

        ApiClient.post(this, ApiConfig.BOOK_ROOM, params, new ApiClient.Callback() {
            @Override public void onSuccess(String response) {
                setLoading(false);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("success".equals(obj.getString("status"))) {
                        int bookingId = obj.optInt("booking_id", -1);
                        showConfirmation(bookingId);
                    } else {
                        Toast.makeText(BookRoomActivity.this,
                                obj.optString("message", "Booking failed"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) { fallbackLocalBooking(); }
            }
            @Override public void onError(String error) { fallbackLocalBooking(); }
        });
    }

    private void fallbackLocalBooking() {
        setLoading(false);
        AppDatabase db = AppDatabase.getInstance(this);
        Room r = db.roomDao().getById(roomId);
        if (r == null || r.occupied >= r.capacity) {
            Toast.makeText(this, "Room not available", Toast.LENGTH_SHORT).show();
            return;
        }
        Booking b = new Booking();
        b.studentId   = sm.getUserId();
        b.roomId      = roomId;
        b.hostelName  = hostelName;
        b.roomNumber  = roomNumber;
        b.roomType    = r.roomType;
        b.totalPrice  = r.pricePerSemester;
        b.status      = "Confirmed";
        b.bookingDate = System.currentTimeMillis();
        b.checkInDone = false;
        int localBookingId = (int) db.bookingDao().insert(b);
        db.roomDao().incrementOccupied(roomId);
        showConfirmation(localBookingId);
    }

    private void showConfirmation(int bookingId) {
        Intent intent = new Intent(this, BookingConfirmationActivity.class);
        intent.putExtra("hostel_name", hostelName);
        intent.putExtra("room_number", roomNumber);
        intent.putExtra("check_in",    checkInDate);
        intent.putExtra("check_out",   checkOutDate);
        intent.putExtra("booking_id",  bookingId);
        startActivity(intent);
        finish();
    }

    private void setLoading(boolean on) {
        progressBar.setVisibility(on ? View.VISIBLE : View.GONE);
        btnBook.setEnabled(!on);
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
