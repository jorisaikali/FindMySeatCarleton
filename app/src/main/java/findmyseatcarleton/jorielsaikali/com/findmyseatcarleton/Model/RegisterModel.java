package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class RegisterModel {

    private final String TAG = "RegisterModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();
    private LiveData<String> result;
    private MutableLiveData<String> rejected = new MutableLiveData<>();
    private String errors = "";

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
        validateFields(username, password, confirmPassword, email, confirmEmail);
        if (!errors.equals("")) {
            errors = errors.substring(0, errors.length() - 1);
            reject(errors);
            return;
        }

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
        LiveData<String> serverResponse = repository.getResult();

        if (serverResponse.getValue().equals("SUCCESS")) {
            result = serverResponse;
            return;
        }

        MutableLiveData<String> parseResponse = parseErrors(serverResponse.getValue());
        Log.i(TAG, "parseResponse: " + parseResponse.getValue());
        result = parseResponse;
    }

    private MutableLiveData<String> parseErrors(String errors) {
        MutableLiveData<String> parsedErrors = new MutableLiveData<>();

        StringBuilder sb = new StringBuilder();
        sb.append("{\"server_response\":").append(errors).append("}");
        String newErrorsString = sb.toString();

        Log.i(TAG, newErrorsString);

        try {
            JSONObject jsonObject = new JSONObject(newErrorsString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int index = 0;
            while (index < jsonArray.length()) {
                //Log.i(TAG, jsonArray.getString(index).substring(8));
                Log.i(TAG, "errors (before): " + errors);
                this.errors += jsonArray.getString(index).substring(8) + ";";
                Log.i(TAG, "errors (after): " + errors);
                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        parsedErrors.setValue(this.errors);

        return parsedErrors;
    }

    private boolean checkFieldsEmpty(String[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].equals("")) {
                return true;
            }
        }

        return false;
    }

    private void validateFields(String username, String password, String confirmPassword, String email, String confirmEmail) {
        // --------- check if any fields are empty ---------- //
        if (checkFieldsEmpty(new String[]{username, password, confirmPassword, email, confirmEmail})) {
            errors += "All fields are required;";
        }
        // -------------------------------------------------- //

        // ---------- Check if username is between 5-25 characters ------------ //
        if (username.length() < 5 || username.length() > 25) {
            errors += "Username must be between 5-25 characters;";
        }
        // -------------------------------------------------------------------- //

        // ----------- Check if password and confirmPassword are the same ------------ //
        if (!password.equals(confirmPassword)) {
            errors += "Passwords do not match;";
        }
        // --------------------------------------------------------------------------- //

        // ------------- Check if password is between 5-25 characters ------------- //
        if (password.length() < 5 || password.length() > 25) {
            errors += "Password must be between 5-25 characters;";
        }
        // ------------------------------------------------------------------------ //

        // ----------- Check if email and confirmEmail are the same ------------ //
        if (!email.equals(confirmEmail)) {
            errors += "Emails do not match;";
        }
        // --------------------------------------------------------------------- //

        // ----------- Check if email contains one @ and at least one . ----------- //
        int periodCount = 0;

        if (email.contains("@")) {
            for (int i = email.indexOf("@"); i < email.length(); i++) {
                if (email.charAt(i) == '.') {
                    periodCount++;
                }
            }
        }

        if (!email.contains("@") || periodCount == 0) {
            errors += "Email is not valid;";
        }
        // ------------------------------------------------------------------------ //
    }

    private String[] encrypt(String data) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data);
    }
}
