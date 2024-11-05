package arep.edu.eci.security;

import arep.edu.eci.dtos.TokenDto;
import arep.edu.eci.models.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * TokenService class.
 * @author Andres Felipe
 */
@Singleton
public class TokenService {

    /** Generate token
     * @param user User object
     * @return TokenDto object
     */
    public TokenDto generateToken(User user) {
        Set<String> roles =  new HashSet<>(
                Arrays.asList("User","Admin")
        );
        String token = Jwt.issuer("twitter-jwt")
                .subject(user.getUserName())
                .groups(roles)
                .expiresAt(
                        System.currentTimeMillis() + 36000
                )
                .sign();
        return new TokenDto(token);
    }
}
