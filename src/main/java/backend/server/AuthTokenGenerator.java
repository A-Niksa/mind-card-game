package backend.server;

import java.security.SecureRandom;
import java.util.Arrays;

public class AuthTokenGenerator {
    private final SecureRandom random;

    public AuthTokenGenerator() {
        random = new SecureRandom();
    }

    public String nextToken() {
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }
}