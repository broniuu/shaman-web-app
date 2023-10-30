package com.example.shamanApi.security;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
/**
 * klasa przechowująca komponenty służace do generowania pary kluczy.
 * */
@Component
final class KeyGeneratorUtils {
    private KeyGeneratorUtils() {}
    /**
     * funcka statyczna generująca parę kluczy w algorytmie RSA.
     *
     * @return      zwraca parę kluczy.
     * */
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
