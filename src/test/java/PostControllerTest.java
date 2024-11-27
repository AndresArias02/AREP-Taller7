import arep.edu.eci.controllers.PostController;
import arep.edu.eci.models.Post;
import arep.edu.eci.services.PostService;
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
 * Post class test
 */
@QuarkusTest
class PostControllerTest {

    @InjectMocks
    PostController postController;

    @Mock
    PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test Get Posts method
     * This test verifies that the Get Posts method returns a 200 status code and a list of Post objects
     */
    @Test
    void testGetPosts() {
        Post post1 = new Post("1", "Post 1");
        Post post2 = new Post("2", "Post 2");

        when(postService.getPosts()).thenReturn(Arrays.asList(post1, post2));

        Response response = postController.getPosts();

        assertEquals(200, response.getStatus());
        assertEquals(2, ((Iterable<Post>) response.getEntity()).spliterator().getExactSizeIfKnown());
    }

    /**
     * Test Get Post method
     * This test verifies that the Get Post method returns a 200 status code and a Post object
     */
    @Test
    void testCreatePost() {
        Post post = new Post("1", "New Post");

        Response response = postController.createPost(post);

        assertEquals(201, response.getStatus());
        verify(postService, times(1)).createPost(post);
    }
}
