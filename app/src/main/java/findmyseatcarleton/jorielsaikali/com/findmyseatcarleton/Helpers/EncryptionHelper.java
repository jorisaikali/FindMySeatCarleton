package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionHelper {
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

    private static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] createSalt() {
        byte[] bytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }
}
