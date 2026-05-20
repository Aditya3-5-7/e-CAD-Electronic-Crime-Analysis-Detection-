package com.example.e_cad;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity8 extends AppCompatActivity {

    private TextView status1, status2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        status1 = findViewById(R.id.status1);
        status2 = findViewById(R.id.status2);

        // Setting dynamic statuses
        updateStatus("PENDING", status1);
        updateStatus("COMPLETED", status2);
    }

    private void updateStatus(String status, TextView view) {
        view.setText(status);
        if (status.equalsIgnoreCase("COMPLETED")) {
            view.setTextColor(Color.parseColor("#138808")); // Green
        } else {
            view.setTextColor(Color.parseColor("#FF9933")); // Orange
        }
    }
}