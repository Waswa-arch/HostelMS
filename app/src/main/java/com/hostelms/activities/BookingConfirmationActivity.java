package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hostelms.R;

/**
 * UPDATED – Booking success confirmation screen.
 *
 * Change: "Proceed to Check-In" button now goes to RoomChecklistActivity
 * (the room-items checklist) instead of straight to the dashboard.
 * booking_id is forwarded so the checklist can mark the booking as done.
 */
public class BookingConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        String hostelName = getIntent().getStringExtra("hostel_name");
        String roomNumber = getIntent().getStringExtra("room_number");
        String checkIn    = getIntent().getStringExtra("check_in");
        String checkOut   = getIntent().getStringExtra("check_out");
        int    bookingId  = getIntent().getIntExtra("booking_id", -1);

        ((TextView) findViewById(R.id.tvHostelName)).setText(hostelName);
        ((TextView) findViewById(R.id.tvRoomNumber)).setText("Room: " + roomNumber);
        ((TextView) findViewById(R.id.tvCheckIn))   .setText("Check-in: "  + checkIn);
        ((TextView) findViewById(R.id.tvCheckOut))  .setText("Check-out: " + checkOut);

        // ── "Proceed to Check-In" → room checklist ────────────────────────
        findViewById(R.id.btnProceedChecklist).setOnClickListener(v -> {
            Intent intent = new Intent(this, RoomChecklistActivity.class);
            intent.putExtra("hostel_name", hostelName);
            intent.putExtra("room_number", roomNumber);
            intent.putExtra("check_in",    checkIn);
            intent.putExtra("check_out",   checkOut);
            intent.putExtra("booking_id",  bookingId);
            startActivity(intent);
            finish();
        });

        // ── "Skip for now" → dashboard (kept as escape hatch) ────────────
        findViewById(R.id.btnSkip).setOnClickListener(v -> {
            startActivity(new Intent(this, StudentDashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });
    }
}
