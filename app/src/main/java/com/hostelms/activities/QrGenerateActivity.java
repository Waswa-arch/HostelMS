package com.hostelms.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.hostelms.R;

public class QrGenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spLocation = findViewById(R.id.spLocation);
        Spinner spType = findViewById(R.id.spType);
        ImageView ivQr = findViewById(R.id.ivQr);
        TextView tvQrContent = findViewById(R.id.tvQrContent);

        String[] locs = {"Library","Mess","Gym","AV Room","Hostel","Laundry Room"};
        String[] types = {"Entry","Exit"};
        spLocation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locs));
        spType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));

        findViewById(R.id.btnGenerate).setOnClickListener(v -> {
            String loc = spLocation.getSelectedItem().toString();
            String type = spType.getSelectedItem().toString();
            String content = "HOSTELMS:" + loc + ":" + type;
            tvQrContent.setText("QR Content: " + content);
            try {
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 600, 600);
                ivQr.setImageBitmap(bitmap);
                ivQr.setVisibility(android.view.View.VISIBLE);
            } catch (WriterException e) {
                Toast.makeText(this, "Failed to generate QR", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
}
