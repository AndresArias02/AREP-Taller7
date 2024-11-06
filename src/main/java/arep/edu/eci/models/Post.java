package arep.edu.eci.models;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Post class
 * This class represents a post
 * @author Andres Felipe
 */
@MongoEntity(collection = "posts")
public class Post extends PanacheMongoEntity {

    private String username;
    private LocalDate creationDate = LocalDate.now();
    @Size(max = 140)
    private String content;

    /**
     *Default constructor
     * This constructor creates a new post with default values
     */
    public Post() {
    }

    /**
     * Constructor
     * This constructor creates a new post with the given parameters
     * @param content the content of the post
     */
    public Post(String username, String content) {
        this.username = username;
        this.content = content;
    }

    /**
     * getUserName method
     * This method returns the name of the user who created the post
     * @return the name of the user who created the post
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUserName method
     * This method sets the name of the user who created the post
     * @param username the name of the user who created the post
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getCreationDate method
     * This method returns the date when the post was created
     * @return the date when the post was created
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * getContent method
     * This method returns the content of the post
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * setContent method
     * This method sets the content of the post
     * @param content the content of the post
     */
    public void setContent(String content) {
        this.content = content;
    }
}
