package by.khaletski.platform.service.util;

import java.math.BigInteger;
import java.util.Base64;

public final class Encoder {

    /**
     * Base64 is a standard for encoding binary data using only 64 ASCII characters. The encoding alphabet contains
     * the text-numeric Latin characters A-Z, a-z and 0-9 (62 characters) and 2 additional characters depending on the
     * implementation system. Every 3 source bytes are encoded with 4 characters (increment by 1⁄₃).
     *
     * @author Anton Khaletski
     */

    private Encoder() {
    }

    public static String encode(String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode(password.getBytes());
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        return bigInt.toString(16);
    }
}
