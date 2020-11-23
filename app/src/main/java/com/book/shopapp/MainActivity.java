package com.book.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //TextView items = findViewById(R.id.items);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void callScanner(View view) {
        Intent intent = new Intent(MainActivity.this, BarcodeScanner.class);
        startActivityForResult(intent, 1);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String item = data.getStringExtra("editTextValue");
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
            }
        }
    }
}