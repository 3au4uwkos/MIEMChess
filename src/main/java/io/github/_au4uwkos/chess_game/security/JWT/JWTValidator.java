package io.github._au4uwkos.chess_game.security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTValidator {
    private final PublicKey key;

    @Autowired
    public JWTValidator(KeyProvider keyProvider) {
        this.key = keyProvider.getPublicKey();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        System.out.println(extractedUsername);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}