package com.example.e_cad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView signUpText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // 1. Initialize Views
            username = findViewById(R.id.username);
            password = findViewById(R.id.Password);
            loginButton = findViewById(R.id.Login);
            signUpText = findViewById(R.id.signUpText); // Ensure this ID is exactly same in XML

            // 2. Login Logic
            if (loginButton != null) {
                loginButton.setOnClickListener(v -> {
                    String userEmail = username.getText().toString().trim();
                    String userPass = password.getText().toString().trim();

                    if (userEmail.equals("kairo@gmail.com") && userPass.equals("1234")) {
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // 3. Sign Up Connection (MainActivity -> MainActivity6)
            if (signUpText != null) {
                signUpText.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, MainActivity6.class);
                    startActivity(intent);
                });
            }

        } catch (Exception e) {
            // Agar crash hone wala hoga toh ye message dikha dega
            Toast.makeText(this, "UI Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}