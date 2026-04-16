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
import java.util.Locale;
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

        TextView tvHostelName = findViewById(R.id.tvHostelName);
        TextView tvRoomNumber = findViewById(R.id.tvRoomNumber);
        TextView tvPrice = findViewById(R.id.tvPrice);

        if (tvHostelName != null) tvHostelName.setText(hostelName);
        if (tvRoomNumber != null) tvRoomNumber.setText("Room " + roomNumber);
        if (tvPrice != null) tvPrice.setText("Price: " + price + "/semester");

        btnCheckIn  = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnBook     = findViewById(R.id.btnBook);
        progressBar = findViewById(R.id.progressBar);

        Spinner spMeal = findViewById(R.id.spMealBundle);
        if (spMeal != null) {
            String[] bundles = {"Room Only", "Bed & Breakfast",
                                "Breakfast & Lunch", "Full Board"};
            spMeal.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, bundles));
        }

        if (btnCheckIn != null) btnCheckIn.setOnClickListener(v -> pickDate(true));
        if (btnCheckOut != null) btnCheckOut.setOnClickListener(v -> pickDate(false));
        if (btnBook != null) btnBook.setOnClickListener(v -> confirmBooking());
    }

    private void pickDate(boolean isCheckIn) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
            String date = y + "-" + String.format(Locale.getDefault(), "%02d", m + 1) + "-" + String.format(Locale.getDefault(), "%02d", d);
            if (isCheckIn) {
                checkInDate = date;
                btnCheckIn.setText("Check-in: " + date);
            } else {
                checkOutDate = date;
                btnCheckOut.setText("Check-out: " + date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
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
        if (progressBar != null) progressBar.setVisibility(on ? View.VISIBLE : View.GONE);
        if (btnBook != null) btnBook.setEnabled(!on);
    }

    @Override public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
