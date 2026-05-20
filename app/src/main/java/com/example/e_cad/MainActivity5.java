package com.example.e_cad;



import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

    Button PayInsuranceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // CONNECT BUTTON
        PayInsuranceButton = findViewById(R.id.PayInsuranceButton);

        // BUTTON CLICK
        PayInsuranceButton.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity5.this,
                    "Redirecting to Insurance Payment...",
                    Toast.LENGTH_SHORT
            ).show();

        });
    }
}