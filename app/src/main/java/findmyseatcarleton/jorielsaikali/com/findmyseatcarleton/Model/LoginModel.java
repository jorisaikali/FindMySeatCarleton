package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers.EncryptionHelper;

public class LoginModel {

    private final String TAG = "LoginModel";

    private EncryptionHelper encryptHelper = new EncryptionHelper();

    public String[] encrypt(String data) throws NoSuchAlgorithmException {
        return encryptHelper.generateHash(data);
    }
}
