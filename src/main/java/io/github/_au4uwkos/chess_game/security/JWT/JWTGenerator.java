package io.github._au4uwkos.chess_game.security.JWT;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JWTGenerator {
    private final PrivateKey privateKey;

    @Autowired
    public JWTGenerator(KeyProvider keyProvider) {
        this.privateKey = keyProvider.getPrivateKey();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(3600))) // 1 час
                .signWith(privateKey)
                .compact();
    }
}