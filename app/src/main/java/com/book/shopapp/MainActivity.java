package com.book.shopapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
                TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2);
                params1.setMargins(10,10,0,0);
                product_name_tv.setLayoutParams(params1);
                product_name_tv.setBackground(getDrawable(R.drawable.colordarkforitemlist));
                product_name_tv.setTextColor(Color.parseColor("#FFFFFF"));
                product_name_tv.setTextSize(18);
                product_name_tv.setText(product_name);
                product_name_tv.setGravity(Gravity.CENTER);


                //create TextView for product Quantity
                TextView product_quant = new TextView(this);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
                params2.setMargins(0,10,0,0);
                product_quant.setLayoutParams(params2);
                product_quant.setBackground(getDrawable(R.drawable.colordarkforitemlist));
                product_quant.setTextColor(Color.parseColor("#FFFFFF"));
                product_quant.setTextSize(18);
                product_quant.setText(Integer.toString(1));
                product_quant.setGravity(Gravity.CENTER);

                //create TextView for product cost
                TextView product_cost_tv = new TextView(this);
                TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
                params3.setMargins(0,10,0,0);
                product_cost_tv.setBackground(getDrawable(R.drawable.colordarkforitemlist));
                product_cost_tv.setTextColor(Color.parseColor("#FFFFFF"));
                product_cost_tv.setLayoutParams(params3);
                product_cost_tv.setTextSize(18);
                product_cost_tv.setText(Integer.toString(product_cost));
                product_cost_tv.setGravity(Gravity.CENTER);




                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                // Add TextViews to TableRow
                tr.addView(product_name_tv,0);
                tr.addView(product_quant,1);
                tr.addView(product_cost_tv,2);

                //Add Row to the Table
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                setContentView(v);
            }
        }
    }
}