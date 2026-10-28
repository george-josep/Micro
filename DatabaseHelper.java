
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "bakeryDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE Orders (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "customer TEXT NOT NULL, " +
                        "item TEXT NOT NULL, " +
                        "quantity INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        // Add database upgrade operations here if required.
    }

    public boolean addOrder(String customer, String item, int quantity) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("customer", customer);
        values.put("item", item);
        values.put("quantity", quantity);

        long result = db.insert("Orders", null, values);

        return result != -1;
    }

    public String getAllOrders() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Orders", null);

        StringBuilder orders = new StringBuilder();

        try {
            while (cursor.moveToNext()) {

                orders.append("Order ID: ")
                        .append(cursor.getInt(0))
                        .append("\nCustomer: ")
                        .append(cursor.getString(1))
                        .append("\nBakery Item: ")
                        .append(cursor.getString(2))
                        .append("\nQuantity: ")
                        .append(cursor.getInt(3))
                        .append("\n\n");
            }
        } finally {
            cursor.close();
        }

        if (orders.length() == 0) {
            return "No orders found";
        }

        return orders.toString();
    }
}
