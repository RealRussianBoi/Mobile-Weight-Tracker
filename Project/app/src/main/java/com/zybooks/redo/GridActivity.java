package com.zybooks.redo;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class GridActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grid);

        dbHelper = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.tableLayout);

        Button setGoalBtn = findViewById(R.id.buttonSetGoalWeight);
        setGoalBtn.setOnClickListener(view -> showGoalWeightDialog());

        Button recordWeightBtn = findViewById(R.id.buttonRecordWeight);
        recordWeightBtn.setOnClickListener(view -> showRecordWeightDialog());

        loadWeightsIntoGrid();  // load data initially
    }

    private void showGoalWeightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Goal Weight");

        final EditText input = new EditText(this);
        input.setHint("Enter goal weight");
        builder.setView(input);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String weight = input.getText().toString().trim();
            if (!weight.isEmpty()) {
                boolean success = dbHelper.setGoalWeight(weight);
                Toast.makeText(this, success ? "Goal set!" : "Failed to set goal", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showRecordWeightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Record New Weight");

        final EditText input = new EditText(this);
        input.setHint("Enter current weight");
        builder.setView(input);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String weight = input.getText().toString().trim();
            if (!weight.isEmpty()) {
                boolean success = dbHelper.addWeight(weight);
                Toast.makeText(this, success ? "Weight recorded!" : "Failed to save", Toast.LENGTH_SHORT).show();
                loadWeightsIntoGrid();  // Refresh grid
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void loadWeightsIntoGrid() {
        // Remove all rows except the header (index 0)
        tableLayout.removeViews(1, Math.max(0, tableLayout.getChildCount() - 1));

        Cursor cursor = dbHelper.getAllWeights();

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id")); // ← Get DB ID
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));

            // Date TextView
            TextView dateView = new TextView(this);
            dateView.setText(date);
            dateView.setPadding(8, 8, 16, 8);

            // Weight TextView
            TextView weightView = new TextView(this);
            weightView.setText(weight + " lbs");
            weightView.setPadding(8, 8, 16, 8);

            // Delete Button
            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(v -> {
                dbHelper.deleteWeight(id);    // ✅ Delete the row from the DB
                loadWeightsIntoGrid();        // ✅ Reload the grid
            });

            row.addView(dateView);
            row.addView(weightView);
            row.addView(deleteButton);

            tableLayout.addView(row);
        }

        cursor.close();
    }

}
