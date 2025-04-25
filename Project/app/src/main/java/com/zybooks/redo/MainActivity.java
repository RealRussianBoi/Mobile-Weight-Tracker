package com.zybooks.redo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register); // Load login_register.xml

        // Initialize DB helper
        dbHelper = new DatabaseHelper(this);

        // Connect XML views to Java
        EditText emailField = findViewById(R.id.editTextEmail);
        EditText passwordField = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);
        TextView forgotPassword = findViewById(R.id.textForgotPassword);

        // LOGIN Button Logic
        loginButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.loginUser(email, password);
            if (success) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                // ✅ Go to SMS Activity
                Intent intent = new Intent(MainActivity.this, SmsActivity.class);
                startActivity(intent);
                finish(); // optional: prevents going back to login
            } else {
                Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
        });

        // REGISTER Button Logic
        registerButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.registerUser(email, password);
            if (success) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String smsPref = prefs.getString("sms_permission", "unset");

                Intent intent;
                if (smsPref.equals("granted") || smsPref.equals("denied")) {
                    intent = new Intent(MainActivity.this, GridActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, SmsActivity.class);
                }

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Registration failed. Email might already exist.", Toast.LENGTH_SHORT).show();
            }
        });

        // FORGOT PASSWORD (placeholder)
        forgotPassword.setOnClickListener(view -> {
            Toast.makeText(this, "Password reset feature coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}
