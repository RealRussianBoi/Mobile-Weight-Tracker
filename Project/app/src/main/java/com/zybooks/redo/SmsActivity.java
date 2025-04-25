package com.zybooks.redo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_SMS_PREF = "sms_permission";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if permission was already accepted or denied
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String smsPref = prefs.getString(KEY_SMS_PREF, "unset");

        if (smsPref.equals("granted") || smsPref.equals("denied")) {
            goToGridActivity();  // Skip screen entirely
            return;
        }

        setContentView(R.layout.activity_sms);

        Button enableButton = findViewById(R.id.buttonEnableSms);
        Button denyButton = findViewById(R.id.buttonDenySms);

        enableButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.SEND_SMS},
                        SMS_PERMISSION_REQUEST_CODE
                );
            } else {
                saveSmsPref("granted");
                goToGridActivity();
            }
        });

        denyButton.setOnClickListener(view -> {
            saveSmsPref("denied");
            Toast.makeText(this, "SMS notifications disabled", Toast.LENGTH_SHORT).show();
            goToGridActivity();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveSmsPref("granted");
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                saveSmsPref("denied");
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
            goToGridActivity();
        }
    }

    private void saveSmsPref(String value) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(KEY_SMS_PREF, value);
        editor.apply();
    }

    private void goToGridActivity() {
        Intent intent = new Intent(SmsActivity.this, GridActivity.class);
        startActivity(intent);
        finish();
    }
}