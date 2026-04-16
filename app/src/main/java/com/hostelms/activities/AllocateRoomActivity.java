package com.hostelms.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.hostelms.R;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.*;
import com.hostelms.utils.NotificationHelper;
import java.util.List;

public class AllocateRoomActivity extends AppCompatActivity {
    private AppDatabase db;
    private Spinner spStudent, spRoom;
    private List<Student> students;
    private List<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_room);
        db = AppDatabase.getInstance(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spStudent = findViewById(R.id.spStudent);
        spRoom = findViewById(R.id.spRoom);
        students = db.studentDao().getAllStudents();
        rooms = db.roomDao().getAvailable();

        String[] studentNames = students.stream().map(s -> s.name + " (" + s.regNumber + ")").toArray(String[]::new);
        String[] roomNames = rooms.stream().map(r -> r.hostelName + " Room " + r.roomNumber + " (" + r.roomType + ") [" + r.occupied + "/" + r.capacity + "]").toArray(String[]::new);
        spStudent.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, studentNames));
        spRoom.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomNames));

        EditText etBed = findViewById(R.id.etBedNumber);
        EditText etOwed = findViewById(R.id.etAmountOwed);

        findViewById(R.id.btnAllocate).setOnClickListener(v -> {
            if (students.isEmpty() || rooms.isEmpty()) {
                Toast.makeText(this, "No students or rooms available", Toast.LENGTH_SHORT).show(); return;
            }
            Student s = students.get(spStudent.getSelectedItemPosition());
            Room r = rooms.get(spRoom.getSelectedItemPosition());
            if (r.occupied >= r.capacity) {
                Toast.makeText(this, "Room is full", Toast.LENGTH_SHORT).show(); return;
            }
            int bed = etBed.getText().toString().isEmpty() ? 1 : Integer.parseInt(etBed.getText().toString());
            double owed = etOwed.getText().toString().isEmpty() ? r.pricePerSemester : Double.parseDouble(etOwed.getText().toString());
            
            db.studentDao().assignRoom(s.id, r.id, bed, r.hostelName, r.roomNumber, r.roomType);

            s.amountOwed = owed; db.studentDao().update(s);
            db.roomDao().incrementOccupied(r.id);
            Toast.makeText(this, s.name + " allocated to " + r.hostelName + " Room " + r.roomNumber, Toast.LENGTH_LONG).show();

            // Send push notification to the student
            NotificationHelper.sendRoomAllocationNotification(
                    this, s.name, r.hostelName, r.roomNumber, bed);
            rooms = db.roomDao().getAvailable();
            String[] rn = rooms.stream().map(ro -> ro.hostelName + " Room " + ro.roomNumber + " (" + ro.roomType + ") [" + ro.occupied + "/" + ro.capacity + "]").toArray(String[]::new);
            spRoom.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rn));
        });
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
