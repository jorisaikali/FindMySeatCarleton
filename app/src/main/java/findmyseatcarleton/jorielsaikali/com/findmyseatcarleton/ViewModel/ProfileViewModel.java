package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.ProfileModel;

public class ProfileViewModel extends ViewModel {
    private final String TAG = "ProfileViewModel";

    private String username, email, currentEntries, totalEntries;

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }
    public String getCurrentEntries() { return currentEntries; }
    public String getTotalEntries() { return totalEntries; }

    public LiveData<String> getResult() {
        ProfileModel profileModel = new ProfileModel(username);

        email = profileModel.getEmail();
        currentEntries = profileModel.getCurrentEntries();
        totalEntries = profileModel.getTotalEntries();

        return profileModel.getResult();
    }

    public LiveData<String> getResultAdmin(int type) {
        ProfileModel profileModel = new ProfileModel(type);
        return profileModel.getResult();
    }
}
