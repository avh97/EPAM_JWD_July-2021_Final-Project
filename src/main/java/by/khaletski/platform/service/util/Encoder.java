package by.khaletski.platform.service.util;

import java.math.BigInteger;
import java.util.Base64;

public final class Encoder {

    private Encoder() {
    }

    /**
     * @param password
     * @return String hash value of a string
     */

    public static String encode(String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode(password.getBytes());
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        return bigInt.toString(16);
    }
}
