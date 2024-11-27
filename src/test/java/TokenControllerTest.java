import arep.edu.eci.controllers.TokenController;
import arep.edu.eci.dtos.TokenDto;
import arep.edu.eci.models.User;
import arep.edu.eci.security.TokenService;
import arep.edu.eci.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Token class test
 */
@QuarkusTest
class TokenControllerTest {

    @InjectMocks
    TokenController tokenController;

    @Mock
    TokenService tokenService;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test Login method
     * This test verifies that the Login method returns a 200 status code and a TokenDto object with the correct token
     */
    @Test
    void testLogin_Success() {
        User user = new User();
        user.setUserName("testUser");
        user.setHashedPassword("hashedPassword");

        when(userService.verifyPassword("testUser", "hashedPassword")).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn(new TokenDto("dummyToken"));

        Response response = tokenController.Login(user);

        assertEquals(200, response.getStatus());
        assertEquals("dummyToken", ((TokenDto) response.getEntity()).getToken());
    }

    /**
     * Test Login method
     * This test verifies that the Login method returns a 401 status code when the username or password is incorrect
     */
    @Test
    void testLogin_Unauthorized() {
        User user = new User();
        user.setUserName("testUser");
        user.setHashedPassword("wrongPassword");

        when(userService.verifyPassword("testUser", "wrongPassword")).thenReturn(false);

        Response response = tokenController.Login(user);

        assertEquals(401, response.getStatus());
    }
}
