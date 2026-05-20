package com.example.e_cad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity7 extends AppCompatActivity {

    EditText complaintBox;

    Button submitComplaint;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        complaintBox =
                findViewById(R.id.complaintBox);

        submitComplaint =
                findViewById(R.id.submitComplaint);

        submitComplaint.setOnClickListener(v -> {

            String complaint =
                    complaintBox.getText()
                            .toString()
                            .trim();

            if (complaint.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please write complaint",
                        Toast.LENGTH_SHORT
                ).show();

            }

            else {

                // FUTURE:
                // SEND TO ADMIN WEBSITE

                Toast.makeText(
                        this,
                        "Complaint Sent Successfully",
                        Toast.LENGTH_LONG
                ).show();

                complaintBox.setText("");

            }

        });

    }
}