
package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2, e3;
    TextView t1;
    Button b1, b2;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);

        t1 = findViewById(R.id.t1);

        dbHelper = new DatabaseHelper(this);
    }

    public void insert(View view) {

        String name = e1.getText().toString().trim();
        String item = e2.getText().toString().trim();
        String quantity = e3.getText().toString().trim();

        if (name.isEmpty() || item.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int qty;

        try {
            qty = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid quantity",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (qty <= 0) {
            Toast.makeText(this, "Quantity must be greater than zero",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.addOrder(name, item, qty)) {

            Toast.makeText(this, "Order added successfully",
                    Toast.LENGTH_SHORT).show();

            e1.setText("");
            e2.setText("");
            e3.setText("");

        } else {
            Toast.makeText(this, "Failed to add order",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void show(View view) {

        String orders = dbHelper.getAllOrders();
        t1.setText(orders);
    }
}
