package com.zybooks.redo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "WeightTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Table: Weights
    public static final String TABLE_WEIGHTS = "weights";
    public static final String COLUMN_WEIGHT_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WEIGHT = "weight";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT)");
        db.execSQL("CREATE TABLE weights (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, weight TEXT)");

        // ✅ Create goal weight table (only stores one row)
        db.execSQL("CREATE TABLE goal_weight (id INTEGER PRIMARY KEY, weight TEXT)");
    }


    // Called if the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTS);
        onCreate(db);
    }

    // Insert new user
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Login check
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE email=? AND password=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean setGoalWeight(String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("goal_weight", null, null); // only keep one goal
        ContentValues values = new ContentValues();
        values.put("weight", weight);
        long result = db.insert("goal_weight", null, values);
        return result != -1;
    }

    public boolean addWeight(String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put("date", date);
        values.put("weight", weight);
        long result = db.insert("weights", null, values);
        return result != -1;
    }

    // Insert new weight entry
    public boolean addWeight(String date, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WEIGHT, weight);
        long result = db.insert(TABLE_WEIGHTS, null, values);
        return result != -1;
    }

    // Get all weights
    public Cursor getAllWeights() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM weights ORDER BY date DESC", null);
    }

    // Delete a weight entry
    public void deleteWeight(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("weights", "id=?", new String[]{String.valueOf(id)});
    }
}