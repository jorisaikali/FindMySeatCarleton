package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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

        // ---------- Getting saved user data on what their two most recent unique scans were ---------- //
        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.username, 0);
        SharedPreferences.Editor editor = pref.edit();

        List<String> seenQRCodes = new ArrayList<>(Arrays.asList(pref.getString("index0", ""), pref.getString("index1", "")));
        BarcodeScannerViewModel bsViewModel = ViewModelProviders.of(this).get(BarcodeScannerViewModel.class);
        bsViewModel.setAlreadySeenQRCodes(seenQRCodes); // setting their two most recent unique scans
        // --------------------------------------------------------------------------------------------- //

        // ----- Checking if user gave permission to use camera ----- //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // request for permission if the user has not given permission
            if (!checkPermission()) {
                requestPermission();
            }
        }
        // ---------------------------------------------------------- //
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(BarcodeScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
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
        // 1. Get text of the scan
        String scanResult = rawResult.getText();

        // 2. Check if scan has FindMySeatCarleton in it (used to make sure user isn't trying to scan other codes)
        if (scanResult.contains("FindMySeatCarleton")) {
            // 3. Set QR data in BarcodeScannerViewModel
            final BarcodeScannerViewModel bsViewModel = ViewModelProviders.of(this).get(BarcodeScannerViewModel.class);
            bsViewModel.setQRData(scanResult.substring(18)); // sends data to bsViewModel without FindMySeatCarleton in it

            // 4. Observe the result in bsViewModel for changes
            bsViewModel.getResult().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    // if the result was successful, display success to user
                    if (!s.equals("REJECTED")) {
                        Toast.makeText(BarcodeScannerActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    // 5. Save new scans to users most recent unique scans
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.username, 0);
                    SharedPreferences.Editor editor = pref.edit();

                    List<String> alreadySeenQRCodes = bsViewModel.getAlreadySeenQRCodes();
                    editor.putString("index0", alreadySeenQRCodes.get(0));
                    editor.putString("index1", alreadySeenQRCodes.get(1));
                    editor.commit();

                    // 6. Resume the camera
                    scannerView.resumeCameraPreview(BarcodeScannerActivity.this);

                }
            });
        }
        // if FindMySeatCarleton is not in scanResult, display to the user error message
        else {
            Toast.makeText(this, "Not a valid FindMySeat QR Code", Toast.LENGTH_SHORT).show();
            scannerView.resumeCameraPreview(BarcodeScannerActivity.this);
        }
    }
}
