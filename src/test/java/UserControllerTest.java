import arep.edu.eci.controllers.UserController;
import arep.edu.eci.models.User;
import arep.edu.eci.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User class Test
 */
@QuarkusTest
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test Get Users method
     * This test verifies that the Get Users method returns a 200 status code and a list of User objects
     */
    @Test
    void testGetUsers() {
        User user1 = new User("1", "user1");
        User user2 = new User("2", "user2");

        when(userService.getUsers()).thenReturn(Arrays.asList(user1, user2));

        Response response = userController.getUsers();

        assertEquals(200, response.getStatus());
        assertEquals(2, ((Iterable<User>) response.getEntity()).spliterator().getExactSizeIfKnown());
    }


    /**
     * Test Create User method
     * This test verifies that the Create User method returns a 201 status code and a User object
     */
    @Test
    void testCreateUser() {
        User user = new User("1", "user1");

        Response response = userController.createUser(user);

        assertEquals(201, response.getStatus());
        verify(userService, times(1)).createUser(user);
    }
}
