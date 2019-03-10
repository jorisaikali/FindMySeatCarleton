package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.ProfileSettingsModel;

public class ProfileSettingsViewModel extends ViewModel {

    private final String TAG = "ProfileSettingsViewModel";

    private String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    public LiveData<String> getResult(String type) {
        ProfileSettingsModel profileSettingsModel = new ProfileSettingsModel(args, type);
        return profileSettingsModel.getResult();
    }

}
