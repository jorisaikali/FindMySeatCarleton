package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View.MainActivity;

public class BarcodeScannerModel {

    private final String TAG = "BarcodeScannerModel";

    private LiveData<String> result;

    public BarcodeScannerModel(String qrData) {
        // qrData comes in the form '{"tableID": 1, "status": 0}'

        String tableID, status;

        // 1. Parse string using JSON and set tableID and status to JSON data
        try {
            JSONObject jsonObject = new JSONObject(qrData);
            tableID = jsonObject.getString("tableID");
            status = jsonObject.getString("status");

            String[] args = {"UPDATE", tableID, status};

            Repository repository = new Repository(args);
            result = repository.getResult();

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

}
