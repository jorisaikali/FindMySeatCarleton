package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class RegisterModel {

    private final String TAG = "RegisterModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;
    private MutableLiveData<String> rejected = new MutableLiveData<>();

    public RegisterModel(String[] args) {
        // args coming in:
        // args[0] = users username
        // args[1] = users password
        // args[2] = users confirmed password
        // args[3] = users email
        // args[4] = users confirmed email

        rejected.setValue("REJECTED");

        runRegisterUser(args[0], args[1], args[2], args[3], args[4]);
    }

    public LiveData<String> getResult() {
        return result;
    }

    private void runRegisterUser(String username, String password, String confirmPassword, String email, String confirmEmail) {
        // ----------- Check if password and confirmPassword are the same ------------ //
        if (!password.equals(confirmPassword)) {
            reject("Passwords do not match");
            return;
        }
        // --------------------------------------------------------------------------- //

        // ----------- Check if email and confirmEmail are the same ------------ //
        if (!email.equals(confirmEmail)) {
            reject("Emails do not match");
            return;
        }
        // --------------------------------------------------------------------- //

        // ---------- Encrypt password user entered with generated salt ---------- //
        String[] encryptedData = null;
        try {
            encryptedData = encrypt(password);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hash = encryptedData[0];
        String salt = encryptedData[1];
        // ----------------------------------------------------------------------- //

        String[] newArgs = {"REGISTER", username, email, salt, hash}; // setting new arguments to pass to Repository

        // ------------ Getting the result of login from Repository ----------- //
        sendToRepository(newArgs);
        // -------------------------------------------------------------------- //
    }

    private void reject(String message) {
        rejected.setValue(message);
        result = rejected;
    }

    private void sendToRepository(String[] args) {
        Repository repository = new Repository(args);
        result = repository.getResult();
    }

    private String[] encrypt(String data) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data);
    }
}
