package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View.RegisterModel;

public class RegisterViewModel extends ViewModel {
    private String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    public LiveData<String> getResult() {
        RegisterModel registerModel = new RegisterModel(this.args);
        return registerModel.getResult();
    }
}
