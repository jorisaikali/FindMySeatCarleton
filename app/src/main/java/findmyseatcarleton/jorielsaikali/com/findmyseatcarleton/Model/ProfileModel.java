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

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getCurrentEntries() { return currentEntries; }
    public String getTotalEntries() { return totalEntries; }

    public LiveData<String> getResult() { return result; }

}
