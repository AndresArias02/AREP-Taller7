package arep.edu.eci.models;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * User class
 * This class represents a user
 * @author Andres Felipe
 */
@MongoEntity(collection = "users")
public class User extends PanacheMongoEntity {

    private String userName;
    private String hashedPassword;

    /**
     * Default constructor
     * This constructor creates a new user with default values
     */
    public User() {
    }

    /**
     * Constructor
     * This constructor creates a new user with the given parameters
     * @param userName the name of the user
     * @param hashedPassword the hashed password of the user
     */
    public User(String userName, String hashedPassword) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
    }


    /**
     * getName method
     * This method returns the name of the user
     * @return the name of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setName method
     * This method sets the name of the user
     * @param userName the name of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getHashedPassword method
     * This method returns the hashed password of the user
     * @return the hashed password of the user
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * setHashedPassword method
     * This method sets the hashed password of the user
     * @param hashedPassword the hashed password of the user
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
