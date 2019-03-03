package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class RegisterModel {

    private final String TAG = "RegisterModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;

    public RegisterModel(String[] args) {
        // ---------- Encrypt password user entered with generated salt ------------ //
        String[] encryptedData = null;
        try {
            encryptedData = encrypt(args[3]);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hash = encryptedData[0];
        String salt = encryptedData[1];
        // --------------------------------------------------------------- //
        String[] newArgs = {"REGISTER", args[1], args[2], salt, hash};

        // ------------ Getting the result of login from Repository ----------- //
        Repository repo = new Repository(newArgs);
        result = repo.getResult();
        // -------------------------------------------------------------------- //
    }

    public LiveData<String> getResult() {
        return result;
    }

    private String[] encrypt(String data) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data);
    }
}
