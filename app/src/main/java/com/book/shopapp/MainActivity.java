package com.book.shopapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LayoutInflater inflater;
    View v;
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = (View) inflater.inflate(R.layout.activity_main, null);
        sv = (ScrollView) v.findViewById(R.id.cart);
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
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                // Add text
                TextView tv = new TextView(this);
                tv.setText(item);
                ll.addView(tv); //Adding TextView to LinearLayout
                // Add the LinearLayout element to the ScrollView
                sv.addView(ll);
                setContentView(v);
            }
        }
    }
}