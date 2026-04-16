package com.hostelms.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.api.ApiClient;
import com.hostelms.api.ApiConfig;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Booking;
import com.hostelms.utils.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RoomChecklistActivity – shown immediately after booking confirmation.
 */
public class RoomChecklistActivity extends AppCompatActivity {

    // ── Checklist items ──────────────────────────────────────────────────────
    private static final ChecklistItem[] ITEMS = {
        new ChecklistItem("🛏️",  "Bed",                    true),
        new ChecklistItem("🧹",  "Broom",                  true),
        new ChecklistItem("🔑",  "Room Key",               true),
        new ChecklistItem("💡",  "Bulb (working)",         true),
        new ChecklistItem("🛏️",  "Mattress",               false),
        new ChecklistItem("🪑",  "Study Chair",            false),
        new ChecklistItem("📚",  "Study Desk",             false),
        new ChecklistItem("🚿",  "Window with Curtains",   false),
        new ChecklistItem("🔌",  "Power Outlets (working)",false),
        new ChecklistItem("🌀",  "Ceiling Fan / AC",       false),
        new ChecklistItem("🗑️",  "Waste Bin",              false),
        new ChecklistItem("🪞",  "Mirror",                 false),
    };

    private static class ChecklistItem {
        final String emoji, label;
        final boolean isRequired;
        ChecklistItem(String emoji, String label, boolean required) {
            this.emoji = emoji; this.label = label; this.isRequired = required;
        }
    }

    private CheckBox[]  checkBoxes;
    private TextView[]  tvStatus;
    private AppDatabase db;
    private SessionManager sm;

    private String hostelName, roomNumber, checkIn, checkOut;
    private int    bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_checklist);

        db = AppDatabase.getInstance(this);
        sm = new SessionManager(this);

        hostelName = getIntent().getStringExtra("hostel_name");
        roomNumber = getIntent().getStringExtra("room_number");
        checkIn    = getIntent().getStringExtra("check_in");
        checkOut   = getIntent().getStringExtra("check_out");
        bookingId  = getIntent().getIntExtra("booking_id", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Room Inspection");
        }

        ((TextView) findViewById(R.id.tvRoomContext))
                .setText(hostelName + "  ·  Room " + roomNumber);

        TextView tvProgress = findViewById(R.id.tvProgress);

        LinearLayout container = findViewById(R.id.checklistContainer);
        checkBoxes = new CheckBox[ITEMS.length];
        tvStatus   = new TextView[ITEMS.length];

        for (int i = 0; i < ITEMS.length; i++) {
            final int idx = i;
            final ChecklistItem item = ITEMS[i];

            View row = LayoutInflater.from(this)
                    .inflate(R.layout.item_room_checklist, container, false);

            ((TextView) row.findViewById(R.id.tvEmoji)).setText(item.emoji);
            TextView tvLabel = row.findViewById(R.id.tvItemLabel);
            tvLabel.setText(item.label);

            TextView tvRequired = row.findViewById(R.id.tvRequired);
            if (item.isRequired) {
                tvRequired.setVisibility(View.VISIBLE);
                tvRequired.setText("Required");
            } else {
                tvRequired.setVisibility(View.GONE);
            }

            tvStatus[idx] = row.findViewById(R.id.tvItemStatus);
            tvStatus[idx].setText("Not checked");
            tvStatus[idx].setTextColor(Color.parseColor("#EF4444"));

            checkBoxes[idx] = row.findViewById(R.id.cbItem);
            checkBoxes[idx].setOnCheckedChangeListener((btn, checked) -> {
                tvStatus[idx].setText(checked ? "✓ Available" : "Not checked");
                tvStatus[idx].setTextColor(checked
                        ? Color.parseColor("#10B981")
                        : Color.parseColor("#EF4444"));
                updateProgress(tvProgress);
            });

            container.addView(row);
        }

        updateProgress(tvProgress);

        findViewById(R.id.btnSelectAll).setOnClickListener(v -> setAll(true,  tvProgress));
        findViewById(R.id.btnClearAll) .setOnClickListener(v -> setAll(false, tvProgress));

        findViewById(R.id.btnSubmitChecklist).setOnClickListener(v -> submitChecklist());
    }

    private void updateProgress(TextView tvProgress) {
        int checked  = countChecked();
        int total    = ITEMS.length;
        int required = 0, requiredDone = 0;
        for (int i = 0; i < ITEMS.length; i++) {
            if (ITEMS[i].isRequired) {
                required++;
                if (checkBoxes[i].isChecked()) requiredDone++;
            }
        }
        tvProgress.setText(checked + " / " + total + " items confirmed"
                + (requiredDone < required
                   ? "  (" + (required - requiredDone) + " required still missing)"
                   : "  ✓ All required items present"));
        tvProgress.setTextColor(requiredDone == required
                ? Color.parseColor("#10B981")
                : Color.parseColor("#F59E0B"));
    }

    private void setAll(boolean check, TextView tvProgress) {
        for (int i = 0; i < checkBoxes.length; i++) checkBoxes[i].setChecked(check);
        updateProgress(tvProgress);
    }

    private int countChecked() {
        int n = 0;
        for (CheckBox cb : checkBoxes) if (cb.isChecked()) n++;
        return n;
    }

    private void submitChecklist() {
        StringBuilder missing = new StringBuilder();
        for (int i = 0; i < ITEMS.length; i++) {
            if (ITEMS[i].isRequired && !checkBoxes[i].isChecked()) {
                missing.append("  • ").append(ITEMS[i].label).append("\n");
            }
        }
        if (missing.length() > 0) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Missing Required Items")
                    .setMessage("Please confirm these items are present before proceeding:\n\n"
                            + missing)
                    .setPositiveButton("Go Back & Check", null)
                    .setNegativeButton("Proceed Anyway", (d, w) -> finalise())
                    .show();
            return;
        }
        finalise();
    }

    private void finalise() {
        int confirmed = countChecked();

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < ITEMS.length; i++) {
            summary.append(ITEMS[i].label)
                   .append(": ")
                   .append(checkBoxes[i].isChecked() ? "Available" : "Not confirmed")
                   .append("\n");
        }

        if (bookingId != -1) {
            Booking b = db.bookingDao().getById(bookingId);
            if (b != null) {
                b.checkInDone = true;
                b.status = "Checked In";
                db.bookingDao().update(b);
            }
        } else {
            List<Booking> bookings = db.bookingDao().getByStudent(sm.getUserId());
            for (Booking b : bookings) {
                if (!b.checkInDone) {
                    b.checkInDone = true;
                    b.status = "Checked In";
                    db.bookingDao().update(b);
                    break;
                }
            }
        }

        Map<String, String> params = new HashMap<>();
        params.put("student_id",    String.valueOf(sm.getUserId()));
        params.put("booking_id",    String.valueOf(bookingId));
        params.put("items_checked", String.valueOf(confirmed));
        params.put("total_items",   String.valueOf(ITEMS.length));
        params.put("summary",       summary.toString());

        ApiClient.post(this, ApiConfig.SUBMIT_CHECKLIST, params, new ApiClient.Callback() {
            @Override public void onSuccess(String r) { }
            @Override public void onError(String e)   { }
        });

        Toast.makeText(this,
                "Check-in complete! " + confirmed + "/" + ITEMS.length + " items confirmed.",
                Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, StudentDashboardActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Skip Checklist?")
                .setMessage("Are you sure you want to skip the room inspection? " +
                        "This is important for your records.")
                .setPositiveButton("Skip", (d, w) -> {
                    startActivity(new Intent(this, StudentDashboardActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                })
                .setNegativeButton("Stay", null)
                .show();
    }
}
