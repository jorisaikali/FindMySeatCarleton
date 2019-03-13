package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View.MainActivity;

public class ProfileModel {
    private final String TAG = "ProfileModel";

    private LiveData<String> result;
    private MutableLiveData<String> rejected = new MutableLiveData<>();
    private String username, email, currentEntries, totalEntries;

    public ProfileModel(String username) {
        String[] args = {"GET PROFILE DATA", username};
        Repository repository = new Repository(args);
        String profileJSON = repository.getResult().getValue();

        try {
            JSONObject jsonObject = new JSONObject(profileJSON);
            this.username = MainActivity.username;
            email = jsonObject.getString("email");
            currentEntries = jsonObject.getString("entries");
            totalEntries = jsonObject.getString("total_entries");

            MutableLiveData<String> response = new MutableLiveData<>();
            response.setValue("SUCCESS");
            result = response;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ProfileModel(int type) {
        // type = 0 for reset entries
        // type = 1 for select winner and email

        String[] args = new String[1];

        if (type == 0) {
            args[0] = "RESET ENTRY";

        }
        else if (type == 1) {
            args[0] = "SELECT WINNER";
        }
        else {
            rejected.setValue("Failed");
            result = rejected;
            return;
        }

        Repository repository = new Repository(args);
        result = repository.getResult();
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getCurrentEntries() { return currentEntries; }
    public String getTotalEntries() { return totalEntries; }

    public LiveData<String> getResult() { return result; }

}
