package com.hostelms.activities;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;
import com.hostelms.utils.SessionManager;

public class CheckInActivity extends AppCompatActivity {
    private static final String[] ITEMS = {
        "Bed frame","Mattress","Pillow(s)","Mattress protector",
        "Study desk","Study chair","Wardrobe / Cabinet","Bookshelf",
        "Window with curtains","Door lock & key","Power outlets (working)",
        "Overhead light (working)","Ceiling fan / AC unit",
        "Mirror","Waste bin","Notice board"
    };
    private CheckBox[] checkBoxes;
    private EditText[] noteFields;
    private AppDatabase db;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        db = AppDatabase.getInstance(this);
        sm = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Student s = db.studentDao().getById(sm.getUserId());
        ((TextView) findViewById(R.id.tvStudentName)).setText(s != null ? s.name : "");

        LinearLayout container = findViewById(R.id.checklistContainer);
        checkBoxes = new CheckBox[ITEMS.length];
        noteFields = new EditText[ITEMS.length];

        for (int i = 0; i < ITEMS.length; i++) {
            final int idx = i;
            View row = LayoutInflater.from(this).inflate(R.layout.item_checklist_row, container, false);
            checkBoxes[i] = row.findViewById(R.id.cbItem);
            checkBoxes[i].setText(ITEMS[i]);
            noteFields[i] = row.findViewById(R.id.etNote);
            Button btnNote = row.findViewById(R.id.btnAddNote);
            btnNote.setOnClickListener(v -> {
                noteFields[idx].setVisibility(
                    noteFields[idx].getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
            container.addView(row);
        }

        findViewById(R.id.btnSubmitCheckin).setOnClickListener(v -> submitCheckin());
    }

    private void submitCheckin() {
        int confirmed = 0;
        StringBuilder notes = new StringBuilder();
        for (int i = 0; i < ITEMS.length; i++) {
            if (checkBoxes[i].isChecked()) confirmed++;
            String note = noteFields[i].getText().toString().trim();
            if (!note.isEmpty()) notes.append(ITEMS[i]).append(": ").append(note).append("\n");
        }
        if (confirmed == 0) {
            Toast.makeText(this, "Please confirm at least one item", Toast.LENGTH_SHORT).show();
            return;
        }
        // Mark booking checked in
        java.util.List<Booking> bookings = db.bookingDao().getByStudent(sm.getUserId());
        for (Booking b : bookings) {
            if (!b.checkInDone) { b.checkInDone = true; b.status = "Checked In"; db.bookingDao().update(b); break; }
        }
        Toast.makeText(this, "Check-in complete! " + confirmed + "/" + ITEMS.length + " items confirmed.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
