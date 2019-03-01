package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class LoginModel {

    private final String TAG = "LoginModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;

    public LoginModel(String[] args) {
        Repository repo = new Repository(args);
        result = repo.getResult();
    }

    public LiveData<String> getResult() {
        return result;
    }

    public String[] encrypt(String data) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data);
    }
}
