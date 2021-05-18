package com.josevahandika.akb_mobile.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.Result;
import com.josevahandika.akb_mobile.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private SharedPref sharedPref;
    private ZXingScannerView mScannerView;
    private static final int PERMISSION_CODE = 1000;
    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        sharedPref = new SharedPref(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        askCameraPermission(Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//    }

    public void askCameraPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(ScanQRActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(ScanQRActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(ScanQRActivity.this,
                    "Permission granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanQRActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(ScanQRActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        mScannerView.stopCamera();
        sharedPref.setIsLogin(true);
        sharedPref.setReservasi(rawResult.getText());
        sharedPref.setIdReservasi(Integer.valueOf(rawResult.getText().split(";")[0]));
        finish();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
       // int id = Integer.parseInt(rawResult.getText());
        //Pendeklarasian queue
        //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

//Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
//untuk request ini tidak memerlukan parameter

//final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_SCAN + id
//        , null, new Response.Listener<JSONObject>() {
//@Override
////            http://127.0.0.1:8000/api/menuCustomer
//public void onResponse(JSONObject response) {
//
//        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
//        try {
//
//        JSONArray jsonArray = response.getJSONArray("data");
//        for (int i = 0; i < jsonArray.length(); i++) {
//        //Mengubah data jsonArray tertentu menjadi json Object
//        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//
////                        int id_reservasi = jsonObject.optInt("id_reservasi");
////                        int id_customer = jsonObject.optInt("id_customer");
////                        int nomor_meja = jsonObject.optInt("nomor_meja");
////
////                        Reservasi reservasi = new Reservasi(id_reservasi, id_customer, nomor_meja);
//
//        }
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        Toast.makeText(getApplicationContext(), response.optString("message"),
//        Toast.LENGTH_SHORT).show();
//        sharedPref.setIdReservasi(id);
//        sharedPref.setIsLogin(true);
//        Intent intent = new Intent(QrCodeActivity.this, MainActivity.class);
//        startActivity(intent);
//        }
//        }, new Response.ErrorListener() {
//@Override
//public void onErrorResponse(VolleyError error) {
//        //Disini bagian jika response jaringan terdapat ganguan/error
//        Toast.makeText(getApplicationContext(), error.getMessage(),
//        Toast.LENGTH_SHORT).show();
//        }
//        });
//        queue.add(stringRequest);
////        mScannerView.resumeCameraPreview(this);
//        }

    }
}