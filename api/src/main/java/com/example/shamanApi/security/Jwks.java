package com.example.shamanApi.security;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
/**
 * klasa uzywana do generowania klucza RSA dla tokena JWK
 * */
public class Jwks {
    private Jwks() {}
    /**
     * funkcja uzywana do generowania kluczy RSA (prywatny oraz publiczny) dla tokena JWK.
     *
     * Wykorzystując klasę {@link  KeyGeneratorUtils KeyGeneratorUtils.}
     * @return       zwraca nową parę kluczy wypelniąną ranodmowym UUID.
     *   */
    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
