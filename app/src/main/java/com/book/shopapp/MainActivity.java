package com.book.shopapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LayoutInflater inflater;
    View v;
    ScrollView sv;
    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_main, null);
        sv = v.findViewById(R.id.cart);
        tl = v.findViewById(R.id.table);
    }

    //Button OnClick for Scanning Barcode
    public void callScanner(View view) {
        Intent intent = new Intent(MainActivity.this, BarcodeScanner.class);
        startActivityForResult(intent, 1);
    }

    //Function that receives scanned data from Barcode Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String item = data.getStringExtra("item");
                List<String> item_list = new ArrayList<String>(Arrays.asList(item.split("\n")));
                String product_name = item_list.get(0);
                int product_cost = Integer.parseInt(item_list.get(1));
                // Create TextView for product name
                TextView product_name_tv = new TextView(this);
                product_name_tv.setTextSize(24);
                product_name_tv.setText(product_name);
                //create TextView for product cost
                TextView product_cost_tv = new TextView(this);
                product_cost_tv.setTextSize(24);
                product_cost_tv.setText(Integer.toString(product_cost));
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                // Add TextViews to TableRow
                tr.addView(product_name_tv,0);
                tr.addView(product_cost_tv,1);
                //Add Row to the Table
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                setContentView(v);
            }
        }
    }
}