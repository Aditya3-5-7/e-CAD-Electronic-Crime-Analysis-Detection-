package com.example.e_cad;

import android.util.Log;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * VirusTotal ka scan result direct Cloud Firestore par report karne ke liye function
     * @param url Scanned website link
     * @param riskScore VirusTotal se mila hua threat percentage (0-100)
     * @param status Alert level string (e.g., "🚨 Dangerous" ya "🟢 Safe")
     */
    public static void reportThreatToWebAdmin(String url, int riskScore, String status) {
        // Data Structure taiyar karna (Map Object)
        Map<String, Object> alertLog = new HashMap<>();
        alertLog.put("url", url);
        alertLog.put("riskScore", riskScore);
        alertLog.put("status", status);
        alertLog.put("timestamp", Timestamp.now()); // Isse admin panel par sorting automatic hogi

        // Firestore ke andar 'alerts' naam ke board par entry push karna
        db.collection("alerts")
                .add(alertLog)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Sync Successful! Document ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Sync Failed to reach Cloud Server", e);
                });
    }
}