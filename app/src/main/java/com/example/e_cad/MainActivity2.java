package com.example.e_cad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

// 🔥 Firebase Cloud Modules
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

// --- 1. VirusTotal API Response Models ---
class VTResponse {
    public Data data;
    public static class Data { public Attributes attributes; }
    public static class Attributes { public Stats last_analysis_stats; }
    public static class Stats {
        public int malicious;
        public int suspicious;
    }
}

// --- 2. Retrofit Interface ---
interface VirusTotalService {
    @GET("urls/{id}")
    Call<VTResponse> checkUrl(@Header("x-apikey") String apiKey, @Path("id") String urlId);
}

public class MainActivity2 extends AppCompatActivity {

    private TextInputEditText urlInput, vehicleNumberInput;
    private TextView linkResult, quickOwner, quickCompany, quickDate;
    private Button checkLinkBtn, searchVehicleBtn, viewChallanBtn, viewInsuranceBtn;
    private ProgressBar scanProgressBar, riskBar;
    private BottomNavigationView bottomNav;
    private LinearLayout actionCheckSMS;

    // Firebase Core Database Instance
    private FirebaseFirestore db;

    private final String MY_API_KEY = "8162427ba45532dc1ff44b23387e0e83701a41d967735511b8f5f4cce2108c80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Firebase Cloud init
        db = FirebaseFirestore.getInstance();

        initViews();

        actionCheckSMS.setOnClickListener(v -> startActivity(new Intent(this, MainActivity7.class)));
        searchVehicleBtn.setOnClickListener(v -> handleVehicleSearch());

        viewInsuranceBtn.setOnClickListener(v -> {
            String vNum = vehicleNumberInput.getText().toString().trim().toUpperCase();
            if (!vNum.isEmpty()) {
                Intent i = new Intent(this, MainActivity5.class);
                i.putExtra("VEHICLE_NO", vNum);
                startActivity(i);
            } else {
                vehicleNumberInput.setError("Enter Vehicle Number");
            }
        });

        viewChallanBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity3.class)));
        checkLinkBtn.setOnClickListener(v -> handleLinkScan());

        setupNavigation();
    }

    private void initViews() {
        urlInput = findViewById(R.id.urlInput);
        linkResult = findViewById(R.id.LinkResult);
        checkLinkBtn = findViewById(R.id.CheckLink);
        scanProgressBar = findViewById(R.id.scanProgressBar);
        riskBar = findViewById(R.id.riskBar);
        vehicleNumberInput = findViewById(R.id.VehicleNumber);
        searchVehicleBtn = findViewById(R.id.SearchVehicle);
        quickOwner = findViewById(R.id.QuickOwner);
        quickCompany = findViewById(R.id.QuickCompany);
        quickDate = findViewById(R.id.QuickDate);
        viewChallanBtn = findViewById(R.id.ViewFineDetailsButton);
        viewInsuranceBtn = findViewById(R.id.ViewInsuranceButton);
        bottomNav = findViewById(R.id.bottomNav);
        actionCheckSMS = findViewById(R.id.actionCheckSMS);
    }

    private void handleVehicleSearch() {
        String vNum = vehicleNumberInput.getText().toString().trim().toUpperCase();
        if (vNum.equals("MP04AD1234")) {
            quickOwner.setText("Owner\nRahul Verma");
            quickCompany.setText("Company\nMercedes");
            quickDate.setText("Reg. Date\n15/01/2003");
        } else if (vNum.equals("MP09AB1111")) {
            quickOwner.setText("Owner\nAditya Tiwari");
            quickCompany.setText("Company\nHonda");
            quickDate.setText("Reg. Date\n12/02/2026");
        } else {
            Toast.makeText(this, "Details not found locally", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLinkScan() {
        String url = urlInput.getText().toString().trim();
        if (url.isEmpty()) {
            urlInput.setError("Paste a link first");
            return;
        }

        scanProgressBar.setVisibility(View.VISIBLE);
        riskBar.setVisibility(View.INVISIBLE);
        linkResult.setText("Analyzing Security...");

        String urlId = Base64.encodeToString(url.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.virustotal.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VirusTotalService service = retrofit.create(VirusTotalService.class);
        service.checkUrl(MY_API_KEY, urlId).enqueue(new Callback<VTResponse>() {
            @Override
            public void onResponse(@NonNull Call<VTResponse> call, @NonNull Response<VTResponse> response) {
                scanProgressBar.setVisibility(View.GONE);
                riskBar.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    int mal = response.body().data.attributes.last_analysis_stats.malicious;
                    int sus = response.body().data.attributes.last_analysis_stats.suspicious;

                    if (mal > 0) {
                        // CASE 1: VIRUS/DANGER
                        linkResult.setText("⚠️ DANGER: VIRUS DETECTED!");
                        linkResult.setTextColor(Color.RED);
                        riskBar.setProgress(100);
                        riskBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        showPopup("🚨 SECURITY ALERT", "Ye link unsafe hai. Isme viruses paye gaye hain!", android.R.drawable.ic_delete);

                        saveToHistory(url, "COMPLETED (DANGER)");
                        // 🔥 Push sync data to web console
                        syncThreatToCloud(url, 100, "🚨 Malicious / Virus");

                    } else if (sus > 0) {
                        // CASE 2: FRAUD/FAKE
                        linkResult.setText("⚠️ WARNING: SUSPICIOUS LINK!");
                        linkResult.setTextColor(Color.parseColor("#FF6D00"));
                        riskBar.setProgress(70);
                        riskBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF6D00")));
                        showPopup("⚠️ FRAUD WARNING", "Ye link suspicious hai. Ye ek fake ya fraud website ho sakti hai.", android.R.drawable.ic_dialog_alert);

                        saveToHistory(url, "PENDING REVIEW");
                        // 🔥 Push sync data to web console
                        syncThreatToCloud(url, 70, "⚠️ Phishing / Suspicious");

                    } else {
                        // CASE 3: SAFE
                        linkResult.setText("✅ SAFE: No Threats Found");
                        linkResult.setTextColor(Color.parseColor("#2E7D32"));
                        riskBar.setProgress(20);
                        riskBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

                        saveToHistory(url, "COMPLETED (SAFE)");
                        // 🔥 Push sync data to web console
                        syncThreatToCloud(url, 0, "🟢 Clean / Safe");
                    }
                } else {
                    linkResult.setText("URL never scanned before.");
                    saveToHistory(url, "PENDING SCAN");
                    syncThreatToCloud(url, 0, "🔍 Unscanned Asset");
                }
            }

            @Override
            public void onFailure(@NonNull Call<VTResponse> call, @NonNull Throwable t) {
                scanProgressBar.setVisibility(View.GONE);
                linkResult.setText("Network Failure!");
            }
        });
    }

    // 🔥 Sync Method to write real-time data to Firestore "alerts" collection
    private void syncThreatToCloud(String url, int riskScore, String status) {
        Map<String, Object> alert = new HashMap<>();
        alert.put("url", url);
        alert.put("riskScore", riskScore);
        alert.put("status", status);
        alert.put("timestamp", Timestamp.now());

        db.collection("alerts")
                .add(alert)
                .addOnSuccessListener(documentReference -> {
                    // Sync done backend callback log
                })
                .addOnFailureListener(e -> {
                    // Error logging silently
                });
    }

    // Professional Popup Helper
    private void showPopup(String title, String message, int icon) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Save report history using SharedPreferences
    private void saveToHistory(String url, String status) {
        SharedPreferences sp = getSharedPreferences("ReportsDB", MODE_PRIVATE);
        String oldData = sp.getString("all_reports", "");
        String newData = url + "|" + status;
        String updated = oldData.isEmpty() ? newData : oldData + ";" + newData;
        sp.edit().putString("all_reports", updated).apply();
    }

    private void setupNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(this, MainActivity4.class));
                return true;
            }
            return true;
        });
    }
}