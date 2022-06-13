package frontend.client;

import java.security.SecureRandom;
import java.util.Arrays;

public class AuthTokenGenerator {
    private static SecureRandom random;

    public AuthTokenGenerator() {
        random = new SecureRandom();
    }

    public static String nextToken() {
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }

}