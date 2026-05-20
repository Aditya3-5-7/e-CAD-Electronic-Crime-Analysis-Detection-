package com.example.e_cad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    TextView OffenseDetail, DetailTotal, PaidAmount, PaidDate;
    Button PayNowButton, btnBack; // Added btnBack

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Challan Report");
        }

        // Connect XML IDs
        OffenseDetail = findViewById(R.id.OffenseDetail);
        DetailTotal = findViewById(R.id.DetailTotal);
        PaidAmount = findViewById(R.id.PaidAmount);
        PaidDate = findViewById(R.id.PaidDate);
        PayNowButton = findViewById(R.id.PayNowButton);


        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }


        Intent intent = getIntent();
        String vehicleNo = intent.getStringExtra("VEHICLE_NO");


        if (vehicleNo != null) {
            if (vehicleNo.equalsIgnoreCase("MP09AB1234")) {
                OffenseDetail.setText("No Helmet / Overspeeding");
                DetailTotal.setText("Rs. 1200");
                PaidAmount.setText("Rs. 2500");
                PaidDate.setText("Last Paid: 15-Mar-2026");
            } else if (vehicleNo.equalsIgnoreCase("MH01AB4321")) {
                OffenseDetail.setText("Wrong Parking");
                DetailTotal.setText("Rs. 850");
                PaidAmount.setText("Rs. 1000");
                PaidDate.setText("Last Paid: 01-Apr-2026");
            } else {
                OffenseDetail.setText("No Major Offense");
                DetailTotal.setText("Rs. 500");
                PaidAmount.setText("Rs. 0");
                PaidDate.setText("No Payment History");
            }
        }

        PayNowButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity3.this, "Opening Payment Gateway...", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}