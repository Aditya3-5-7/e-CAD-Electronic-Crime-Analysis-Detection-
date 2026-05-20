package com.example.e_cad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity6 extends AppCompatActivity {

    // XML se connect karne ke liye variables
    private EditText regName, regMobile, regEmail, regUsername, regPassword;
    private Button registerButton;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tumhara bheja hua layout load ho raha hai
        setContentView(R.layout.activity_main6);

        // 1. Views ko initialize karein (IDs tumhare XML se match hain)
        regName = findViewById(R.id.regName);
        regMobile = findViewById(R.id.regMobile);
        regEmail = findViewById(R.id.regEmail);
        regUsername = findViewById(R.id.regUsername);
        regPassword = findViewById(R.id.regPassword);
        registerButton = findViewById(R.id.RegisterButton);
        bottomNav = findViewById(R.id.bottomNavRegister);

        // 2. Register Button Logic
        if (registerButton != null) {
            registerButton.setOnClickListener(v -> {
                String name = regName.getText().toString().trim();
                String mobile = regMobile.getText().toString().trim();

                if (name.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registration Successful, " + name, Toast.LENGTH_SHORT).show();

                    // Registration ke baad Login (MainActivity) par bhejein
                    Intent intent = new Intent(MainActivity6.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Registration screen band ho jayegi
                }
            });
        }

        // 3. Bottom Navigation Setup (Taaki user Register screen se bhi Home/Profile ja sake)
        if (bottomNav != null) {
            setupNavigation();
        }
    }

    private void setupNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(MainActivity6.this, MainActivity2.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                // Profile page (Activity 4) par bhejein
                startActivity(new Intent(MainActivity6.this, MainActivity4.class));
                finish();
                return true;
            }
            return false;
        });
    }
}