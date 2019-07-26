package io.github.assets.app.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

import static io.github.assets.app.AppConstants.TOKEN_BYTE_LENGTH;

/**
 * Used as token generator for messaging
 */
@Component("tokenGenerator")
public class TokenGenerator {

    public String generateHexToken() {

        return generateHexToken(TOKEN_BYTE_LENGTH);
    }

    public String generateBase64Token() {

        return generateBase64Token(TOKEN_BYTE_LENGTH);
    }

    //generateRandomHexToken(16) -> 2189df7475e96aa3982dbeab266497cd
    public String generateHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }

    //generateRandomBase64Token(16) -> EEcCCAYuUcQk7IuzdaPzrg
    public String generateBase64Token(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }

}
