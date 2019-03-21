package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;

public class BarcodeScannerModel {

    private final String TAG = "BarcodeScannerModel";

    private LiveData<String> result;
    private static List<String> alreadySeenQRCodes = new ArrayList<>(Arrays.asList("", ""));

    public BarcodeScannerModel() {}

    public BarcodeScannerModel(String qrData) {
        // qrData comes in the form '{"tableID": 1, "status": 0}' (this is an example)

        // Check if the scan is part of users two most recent unique scans...
        if (alreadySeenQRCodes.contains(qrData)) {
            // if so, send REJECTED back to BarcodeScannerViewModel
            MutableLiveData<String> rejectedResult = new MutableLiveData<>();
            rejectedResult.setValue("REJECTED");
            result = rejectedResult;
            return;
        }

        String tableID, status;

        // 1. Parse string using JSON and set tableID and status to JSON data
        try {
            JSONObject jsonObject = new JSONObject(qrData);
            tableID = jsonObject.getString("tableID");
            status = jsonObject.getString("status");

            // Setting arguments for Repository to use
            String[] args = {"UPDATE", tableID, status};

            // Setting QR code to two recent unique scans
            alreadySeenQRCodes.set(0, alreadySeenQRCodes.get(1));
            alreadySeenQRCodes.set(1, qrData);

            // Getting result from repository
            Repository repository = new Repository(args);
            result = repository.getResult();

            // if result is not null, give user an entry
            if (result != null) {
                Repository addEntryRepo = new Repository(new String[]{"ADD ENTRY"});
                addEntryRepo.getResult();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LiveData<String> getResult() {
        return result;
    }

    public List<String> getAlreadySeenQRCodes() { return alreadySeenQRCodes; }
    public void setAlreadySeenQRCodes(List<String> seenQRCodes) { alreadySeenQRCodes =  seenQRCodes; }
}
