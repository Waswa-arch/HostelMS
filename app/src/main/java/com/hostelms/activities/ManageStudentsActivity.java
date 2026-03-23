package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hostelms.R;
import com.hostelms.adapters.StudentAdapter;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Student;
import java.util.ArrayList;
import java.util.List;

public class ManageStudentsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private AppDatabase db;
    private List<Student> allStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        db = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv = findViewById(R.id.rvStudents);
        rv.setLayoutManager(new LinearLayoutManager(this));

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) { filter(s.toString()); }
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddEditStudentActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        allStudents = db.studentDao().getAllStudents();
        rv.setAdapter(new StudentAdapter(allStudents, s -> {
            Intent i = new Intent(this, AddEditStudentActivity.class);
            i.putExtra("studentId", s.id);
            startActivity(i);
        }));
    }

    private void filter(String query) {
        List<Student> filtered = new ArrayList<>();
        for (Student s : allStudents) {
            if (s.name.toLowerCase().contains(query.toLowerCase())
                    || s.regNumber.toLowerCase().contains(query.toLowerCase()))
                filtered.add(s);
        }
        rv.setAdapter(new StudentAdapter(filtered, s -> {
            Intent i = new Intent(this, AddEditStudentActivity.class);
            i.putExtra("studentId", s.id);
            startActivity(i);
        }));
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
