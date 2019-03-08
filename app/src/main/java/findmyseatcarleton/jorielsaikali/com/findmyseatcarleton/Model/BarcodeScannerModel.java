package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View.MainActivity;

public class BarcodeScannerModel {

    private final String TAG = "BarcodeScannerModel";

    private LiveData<String> result;
    private static List<String> alreadySeenQRCodes = new ArrayList<>(Arrays.asList("", ""));

    public BarcodeScannerModel() {}

    public BarcodeScannerModel(String qrData) {
        // qrData comes in the form '{"tableID": 1, "status": 0}'

        if (alreadySeenQRCodes.contains(qrData)) {
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

            String[] args = {"UPDATE", tableID, status};

            alreadySeenQRCodes.set(0, alreadySeenQRCodes.get(1));
            alreadySeenQRCodes.set(1, qrData);

            for (int i = 0; i < alreadySeenQRCodes.size(); i++) {
                Log.i(TAG, "alreadySeenQRCodes[" + i + "]: " + alreadySeenQRCodes.get(i));
            }

            Repository repository = new Repository(args);
            result = repository.getResult();

            if (result != null) {
                Repository addEntryRepo = new Repository(new String[]{"ADD ENTRY"});
                addEntryRepo.getResult();
                Log.i(TAG, "You got an entry!");
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
