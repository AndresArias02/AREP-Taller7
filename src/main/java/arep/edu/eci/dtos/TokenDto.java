package arep.edu.eci.dtos;

/**
 * TokenDto class.
 * @author Andres Felipe
 */
public class TokenDto {

    private String Token;

    /**
     * Constructor.
     * @param token Token.
     */
    public TokenDto(String token) {
        Token = token;
    }

    /**
     * Get token.
     * @return Token.
     */
    public String getToken() {
        return Token;
    }

    /**
     * Set token.
     * @param token Token.
     */
    public void setToken(String token) {
        Token = token;
    }
}
