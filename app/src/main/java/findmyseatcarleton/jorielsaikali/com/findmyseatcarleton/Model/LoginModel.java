package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class LoginModel {

    private final String TAG = "LoginModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;
    private MutableLiveData<String> rejected = new MutableLiveData<>();

    public LoginModel(String[] args) {
        // args[0] = username
        // args[1] = password

        // ------------ Checking if all fields filled in ------------ //
        if (checkFieldsEmpty(args)) {
            reject("All fields required");
            return;
        }
        // ---------------------------------------------------------- //

        // ----------- Get salt from Repository ------------ //
        String[] getSaltArgs = {"GET SALT", args[0]};
        Repository getSaltRepo = new Repository(getSaltArgs);
        String salt = getSaltRepo.getResult().getValue().replace("\"", "");
        // -------------------------------------------------- //

        // failed salt request returns "FAILED LOGIN: Username or password is incorrect"
        if (salt.equals("FAILED LOGIN: Username or password is incorrect")) {
            // Failed getting salt (username does not exist)
            reject("Username or password is incorrect");
            return;
        }

        // ---------- Encrypt password user entered with salt ------------ //
        String encryptedPassword = null;
        try {
            encryptedPassword = encrypt(args[1], salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // --------------------------------------------------------------- //

        // checks if encrypting the password somehow failed
        if (encryptedPassword == null) {
            reject("Error occurred while logging in");
            return;
        }

        String[] newArgs = {"LOGIN", args[0], encryptedPassword}; // setting new arguments to be passed to Repository

        // ------------ Getting the result of login from Repository ----------- //
        Repository repo = new Repository(newArgs);
        result = repo.getResult(); // triggers the observe from LoginFragment
        // -------------------------------------------------------------------- //
    }

    public LiveData<String> getResult() {
        return result;
    }

    private void reject(String message) {
        rejected.setValue(message);
        result = rejected; // triggers the observe from LoginFragment with the errors
    }

    private boolean checkFieldsEmpty(String[] fields) {
        // ------------ Iterate through all given fields ------------ //
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].equals("")) {
                return true; // return true if a field is empty
            }
        }
        // ---------------------------------------------------------- //

        // otherwise return false
        return false;
    }

    private String encrypt(String data, String salt) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data, salt);
    }
}
