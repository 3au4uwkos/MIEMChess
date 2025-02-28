package io.github._au4uwkos.chess_game.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class KeyProvider {
    private final KeyPair keyPair;

    public KeyProvider() {
        this.keyPair = Jwts.SIG.ES256.keyPair().build();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
}
