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
    private LocalDate creationDate;
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
        this.creationDate = LocalDate.now();
        this.content = content;
    }

    /**
     * getUserName method
     * This method returns the name of the user who created the post
     * @return the name of the user who created the post
     */
    public String getUserName() {
        return username;
    }

    /**
     * setUserName method
     * This method sets the name of the user who created the post
     * @param userName the name of the user who created the post
     */
    public void setUserName(String userName) {
        this.username = userName;
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
     * setCreationDate method
     * This method sets the date when the post was created
     * @param creationDate the date when the post was created
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
