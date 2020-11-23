package com.book.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.HashMap;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;

public class BarcodeScanner extends AppCompatActivity {

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    HashMap<String, String> products_name = new HashMap<String, String>();
    HashMap<String, Integer> products_cost = new HashMap<String, Integer>();
    String product_name;
    int product_cost;
    String product_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        products_name.put("8901287100211","Mysore Sandal");
        products_cost.put("8901287100211",62);
        products_name.put("8901571004614","Sensodyne Toothpaste");
        products_cost.put("8901571004614",110);
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);
        initialiseDetectorsAndSources();
    }

    //Function that initialises and uses the barcode scanner
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(BarcodeScanner.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(BarcodeScanner.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                //If the scanned product is in the database get its details
                                if(products_name.containsKey(barcodeData)) {
                                    product_name=products_name.get(barcodeData);
                                    product_cost=products_cost.get(barcodeData);
                                    product_details = product_name+"\nCost = Rs."+product_cost;
                                    barcodeText.setText(product_details);
                                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                }
                                else{
                                    barcodeText.setText("Please scan again");
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //Add the scanned item to cart by sending it's details to main activity
    public void addItemToCart(View view) {
        Intent intent = new Intent();
        intent.putExtra("item", product_details);
        setResult(RESULT_OK, intent);
        finish();
    }
}