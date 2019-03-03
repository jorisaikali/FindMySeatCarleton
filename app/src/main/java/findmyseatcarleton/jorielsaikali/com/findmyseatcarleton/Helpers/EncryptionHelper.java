package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionHelper {
    private final String TAG = "EncryptionHelper";

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private String algorithm = "SHA-512";

    public String[] generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();

        byte[] salt = createSalt();
        digest.update(salt);

        byte[] hash = digest.digest(data.getBytes());

        String[] generatedData = {bytesToStringHex(hash), bytesToStringHex(salt)};

        return generatedData;
    }

    public String generateHash(String data, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        digest.update(hexStringToByteArray(salt));

        byte[] hash = digest.digest(data.getBytes());

        String generatedData = bytesToStringHex(hash);

        return generatedData;
    }

    private static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static byte[] createSalt() {
        byte[] bytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }
}
