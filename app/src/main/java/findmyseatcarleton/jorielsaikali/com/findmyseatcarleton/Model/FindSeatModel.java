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
    private LiveData<String> result;

    public FindSeatModel(String type, String building) {
        String[] args;

        // if type is for BUILDINGS, FLOORS, or final search (getting available tables based on search)
        if (type.equals("BUILDINGS")) {
            args = new String[2];
            args[0] = "BUILDING LIST";
            args[1] = "buildingList";
        }
        else if (type.equals("FLOORS")) {
            args = new String[3];
            args[0] = "FLOOR LIST";
            args[1] = "floorList";
            args[2] = building;
        }
        else {
            args = new String[0];
        }

        // ------- Creating and getting result from Repository ------ //
        Repository repository = new Repository(args);
        String resultString = repository.getResult().getValue();
        // ---------------------------------------------------------- //

        // Add '{"server_response":' to the beginning of coordinatesString and "}" to the end
        StringBuilder sb = new StringBuilder();
        sb.append("{\"server_response\":").append(resultString).append("}");
        String newResultString = sb.toString();

        List<String> results = new ArrayList<>();

        // -------------- Parsing JSON --------------- //
        try {
            JSONObject jsonObject = new JSONObject(newResultString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int index = 0;

            if (type.equals("BUILDINGS")) {
                while (index < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(index);
                    results.add(jo.getString("name"));
                    index++;
                }
            }
            else if (type.equals("FLOORS")) {
                while (index < jsonArray.length()) {
                    String level = jsonArray.getString(index);
                    results.add(level);
                    index++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // ------------------------------------------- //

        resultList.setValue(results);
    }

    // for finding, we need to POST a 'find', 'number_of_seats', 'buildingID', 'floor'
    public FindSeatModel(String seatAmount, String building, String floor) {
        String[] args = {"FIND", seatAmount, building, floor};
        Repository repository = new Repository(args);
        result = repository.getResult();
    }

    public LiveData<List<String>> getResultList() {
        return resultList;
    }
    public LiveData<String> getResult() { return result; }
}
