package com.hostelms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hostelms.database.AppDatabase;
import com.hostelms.database.entities.Attendance;
import com.hostelms.utils.SessionManager;

public class QrScanActivity extends AppCompatActivity {
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = new SessionManager(this);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan the QR code at the entrance");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String content = result.getContents();
            if (content == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            // Expected QR content format: "HOSTELMS:LOCATION:TYPE" e.g. "HOSTELMS:Library:Entry"
            String[] parts = content.split(":");
            String location = "Unknown";
            String type = "Entry";
            if (parts.length >= 3 && "HOSTELMS".equals(parts[0])) {
                location = parts[1];
                type = parts[2];
            } else {
                location = content; // fallback: use raw QR content as location
            }

            Attendance a = new Attendance();
            a.studentId = sm.getUserId();
            a.studentName = sm.getName();
            a.location = location;
            a.type = type;
            a.timestamp = System.currentTimeMillis();
            AppDatabase.getInstance(this).attendanceDao().insert(a);

            Toast.makeText(this, type + " logged: " + location, Toast.LENGTH_LONG).show();
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
