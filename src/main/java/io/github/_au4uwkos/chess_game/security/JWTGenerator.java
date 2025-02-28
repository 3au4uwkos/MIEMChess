package io.github._au4uwkos.chess_game.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.Date;

@Component
public class JWTGenerator {

    @Value("${jwt.expiration:60000}")
    private long JWT_EXPIRATION;

    private final PrivateKey privateKey;

    @Autowired
    public JWTGenerator(KeyProvider keyProvider) {
        this.privateKey = keyProvider.getPrivateKey();
    }


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiredDate)
                .signWith(this.privateKey)
                .compact();
    }
}
