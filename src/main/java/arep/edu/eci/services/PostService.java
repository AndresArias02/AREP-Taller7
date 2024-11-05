package arep.edu.eci.services;

import arep.edu.eci.models.Post;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
/**
 * PostService class
 * This class is responsible for handling the operations related to the posts
 * @author Andres Felipe
 */
@ApplicationScoped
public class PostService {

    /**
     * getPosts method
     * This method returns a list of posts
     * @return a list of posts
     */
    public List<Post> getPosts() {
        return Post.listAll();
    }

    /**
     * getPost method
     * This method returns a post with the given id
     * @param id the id of the post to be returned
     * @return the post with the given id
     */
    public Post getPost(String id) {
        return Post.findById(id);
    }

    /**
     * createPost method
     * This method creates a new post
     * @param post the post to be created
     */
    public void createPost(Post post) {
        post.persist();
    }

    /**
     * updatePost method
     * This method updates a post with the given id
     * @param existingPost the post to be updated
     * @param post the post to be updated
     * @return the updated post
     */
    public Post updatePost(Post existingPost, Post post) {
        existingPost.update(post);
        return existingPost;
    }

    /**
     * deletePost method
     * This method deletes a post with the given id
     * @param id the id of the post to be deleted
     */
    public void deletePost(String id) {
        Post.deleteById(id);
    }
}
