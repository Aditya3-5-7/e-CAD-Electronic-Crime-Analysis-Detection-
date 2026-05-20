package com.example.e_cad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    private Button btnViewReports, btnLogout;
    private TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // 1. Load your Profile Layout
            setContentView(R.layout.activity_main4);

            // 2. Initialize Views using IDs from your XML
            profileName = findViewById(R.id.profileName);
            btnViewReports = findViewById(R.id.btnViewReports);
            btnLogout = findViewById(R.id.btnLogout);

            // Set static data
            if (profileName != null) {
                profileName.setText("Aditya Tiwari");
            }

            // 3. View Reports Click -> MainActivity8
            if (btnViewReports != null) {
                btnViewReports.setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity4.this, MainActivity8.class));
                });
            }

            // 4. Logout Click -> Back to Login
            if (btnLogout != null) {
                btnLogout.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }

        } catch (Exception e) {
            // Agar crash ho raha ho toh yahan message dikhega
            Toast.makeText(this, "Layout Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}