package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.BarcodeScannerViewModel;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class BarcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.username, 0);
        SharedPreferences.Editor editor = pref.edit();

        List<String> seenQRCodes = new ArrayList<>(Arrays.asList(pref.getString("index0", ""), pref.getString("index1", "")));
        BarcodeScannerViewModel bsViewModel = ViewModelProviders.of(this).get(BarcodeScannerViewModel.class);
        bsViewModel.setAlreadySeenQRCodes(seenQRCodes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {

            }
            else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(BarcodeScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        /*
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {

                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access to camera in order to scan QR codes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }*/
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(BarcodeScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }

                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String scanResult = rawResult.getText();

        if (scanResult.contains("FindMySeatCarleton")) {
            //Toast.makeText(this, scanResult, Toast.LENGTH_LONG).show();

            final BarcodeScannerViewModel bsViewModel = ViewModelProviders.of(this).get(BarcodeScannerViewModel.class);
            bsViewModel.setQRData(scanResult.substring(18));

            bsViewModel.getResult().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    if (!s.equals("REJECTED")) {
                        Toast.makeText(BarcodeScannerActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.username, 0);
                    SharedPreferences.Editor editor = pref.edit();

                    List<String> alreadySeenQRCodes = bsViewModel.getAlreadySeenQRCodes();
                    editor.putString("index0", alreadySeenQRCodes.get(0));
                    editor.putString("index1", alreadySeenQRCodes.get(1));
                    editor.commit();

                    scannerView.resumeCameraPreview(BarcodeScannerActivity.this);

                }
            });
        }
        else {
            Toast.makeText(this, "Not a valid FindMySeat QR Code", Toast.LENGTH_SHORT).show();
            scannerView.resumeCameraPreview(BarcodeScannerActivity.this);
        }
    }
}
