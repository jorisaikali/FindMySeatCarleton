package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.LoginModel;

public class LoginViewModel extends ViewModel {

    private final String TAG = "LoginViewModel";

    private String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    public LiveData<String> getResult() throws NoSuchAlgorithmException {
        LoginModel loginModel = new LoginModel(this.args);
        return loginModel.getResult();
    }

}
