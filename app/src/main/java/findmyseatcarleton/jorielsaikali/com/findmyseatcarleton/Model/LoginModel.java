package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class LoginModel {

    private final String TAG = "LoginModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;

    public LoginModel(String[] args) throws NoSuchAlgorithmException {
        // ----------- Get salt from remote data ------------ //
        String[] getSaltArgs = {"GET SALT", args[1]};
        Repository getSaltRepo = new Repository(getSaltArgs);
        String salt = getSaltRepo.getResult().getValue();
        // -------------------------------------------------- //

        // ---------- Encrypt password user entered with salt ------------ //
        String encryptedPassword = encrypt(args[2], salt.getBytes());
        // --------------------------------------------------------------- //

        args[2] = encryptedPassword; // Set args[2] to encrypted password so Repository can search with new encrypted password

        // ------------ Getting the result of login from Repository ----------- //
        Repository repo = new Repository(args);
        result = repo.getResult();
        // -------------------------------------------------------------------- //
    }

    public LiveData<String> getResult() {
        return result;
    }

    private String encrypt(String data, byte[] salt) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data, salt);
    }
}
