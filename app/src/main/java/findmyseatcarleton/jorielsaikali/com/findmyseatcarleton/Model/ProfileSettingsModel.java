package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class ProfileSettingsModel {

    private final String TAG = "ProfileSettingsModel";

    private EncryptionHelper encryptionHelper = new EncryptionHelper();
    private LiveData<String> result;
    private MutableLiveData<String> rejected = new MutableLiveData<>();

    public ProfileSettingsModel(String[] args, String type) {
        // if type = PASSWORD
        //      args[0] = users username
        //      args[1] = users old password
        //      args[2] = users new password
        //      args[3] = users confirmed new password

        // if type = EMAIL
        //      args[0] = users username
        //      args[1] = users new email
        //      args[2] = users confirmed new email

        rejected.setValue("REJECTED"); // this will be used if anything goes wrong on the changing password/email process

        // ---------------- Check which type needs to be done ---------------- //
        if (type.equals("PASSWORD")) {
            runChangePassword(args[0], args[1], args[2], args[3]);
        }
        else if (type.equals("EMAIL")) {
            runChangeEmail(args[0], args[1], args[2]);
        }
        else {
            result = rejected;
            return;
        }
        // ------------------------------------------------------------------- //
    }

    public LiveData<String> getResult() { return result; }

    private void runChangePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        // --------- Check if newPassword and confirmPassword are the same --------- //
        if (!newPassword.equals(confirmPassword)) {
            reject("Passwords do not match");
            return;
        }
        // ------------------------------------------------------------------------- //

        // --------- Get Salt from Repository using username --------- //
        String[] getSaltArgs = {"GET SALT", username};
        String salt = sendToRepository(getSaltArgs, true).replace("\"", "");
        // ----------------------------------------------------------- //

        Log.i(TAG, "salt: " + salt);

        // ----- Encrypt oldPassword and send to Repository to check if the same as users old password ----- //
        String encryptedOldPassword = encrypt(oldPassword, salt);

        Log.i(TAG, "encryptedOldPassword: " + encryptedOldPassword);

        if (encryptedOldPassword.equals("")) {
            reject("FAILED: Error occurred while encrypting old password");
            return;
        }

        String[] encryptOPArgs = {"CONFIRM OLD PASSWORD", username, encryptedOldPassword};
        if (!sendToRepository(encryptOPArgs, true).equals("SUCCESS")) {
            reject("Old password is not correct");
            return;
        }
        // ------------------------------------------------------------------------------------------------- //

        // ----- Encrypt newPassword and send to Repository to update users password to new password ----- //
        String encryptedNewPassword = encrypt(newPassword, salt);

        if (encryptedNewPassword.equals("")) {
            reject("FAILED: Error occurred while encrypting new password");
            return;
        }

        String[] encryptNPArgs = {"UPDATE PASSWORD", username, encryptedNewPassword};
        sendToRepository(encryptNPArgs, false);
        // ----------------------------------------------------------------------------------------------- //
    }

    private void runChangeEmail(String username, String newEmail, String confirmEmail) {
        // --------- Check if newEmail and confirmEmail are the same --------- //
        if (!newEmail.equals(confirmEmail)) {
            return;
        }
        // ------------------------------------------------------------------------- //

        // ----- Send newEmail to Repository to update users email to new email ----- //
        String[] newEmailArgs = {"UPDATE EMAIL", username, newEmail};
        sendToRepository(newEmailArgs, false);
        // -------------------------------------------------------------------------- //
    }

    private void reject(String message) {
        rejected.setValue(message);
        result = rejected;
    }

    private String sendToRepository(String[] args, boolean flag) {
        // flag is used to return a String value (true) or just set result (false)
        Repository repository = new Repository(args);

        if (flag) {
            return repository.getResult().getValue();
        }

        result = repository.getResult();
        return null;
    }

    private String encrypt(String data, String salt) {
        String encryptedData = "";

        try {
            encryptedData = encryptionHelper.generateHash(data, salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedData;
    }

}
