package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;

public class FindSeatModel {

    private final String TAG = "FindSeatModel";

    private MutableLiveData<List<String>> resultList = new MutableLiveData<>();

    public FindSeatModel(String type, String floor) {
        String[] args;

        if (type.equals("BUILDINGS")) {
            args = new String[2];
            args[0] = "BUILDING LIST";
            args[1] = "buildingList";
        }
        else if (type.equals("FLOORS")) {
            args = new String[3];
            args[0] = "FLOOR LIST";
            args[1] = "floorList";
            args[2] = floor;
        }
        else {
            args = new String[0];
        }

        Repository repository = new Repository(args);
        String resultString = repository.getResult().getValue();

        // Add '{"server_response":' to the beginning of coordinatesString and "}" to the end
        StringBuilder sb = new StringBuilder();
        sb.append("{\"server_response\":").append(resultString).append("}");
        String newResultString = sb.toString();

        Log.i(TAG, newResultString);

        List<String> results = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(newResultString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int index = 0;
            while (index < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(index);
                results.add(jo.getString("name"));
                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*for (int i = 0; i < results.size(); i++) {
            Log.i(TAG, "resultList.get(i): " + results.get(i));
        }*/

        resultList.setValue(results);
    }

    // for finding, we need to POST a 'find', 'number_of_seats', 'buildingID', 'floor'
    public FindSeatModel(String seatAmount, String building, String floor) {
        String[] args = {"FIND", seatAmount, building, floor};

    }

    public LiveData<List<String>> getResultList() {
        return resultList;
    }
}
