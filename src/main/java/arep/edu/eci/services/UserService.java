package arep.edu.eci.services;

import arep.edu.eci.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * UserService class
 * This class is responsible for handling the operations related to the users
 * @author Andres Felipe
 */
@ApplicationScoped
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * updateUser method
     * This method updates a user with the given id
     * @param existingUser the user to be updated
     * @param user the user to be updated
     * @return the updated user
     */
    public User updateUser(User existingUser, User user) {
        existingUser.setUserName(user.getUserName());
        existingUser.setHashedPassword(hashOfPassword(user.getHashedPassword()));
        existingUser.update();
        return existingUser;
    }

    /**
     * createUser method
     * This method creates a new user
     * @param user the user to be created
     * @return the created user
     */
    public User createUser(User user) {
        user.setHashedPassword(hashOfPassword(user.getHashedPassword()));
        user.persist();
        return user;
    }

    /**
     * getUsers method
     * This method returns a list of users
     * @return a list of users
     */
    public List<User> getUsers() {
        return User.listAll();
    }

    /**
     * getUser method
     * This method returns a user with the given id
     * @param id the id of the user to be returned
     * @return the user with the given id
     */
    public User getUser(String id) {
        return User.findById(id);
    }

    /**
     * deleteUser method
     * This method deletes a user with the given id
     * @param id the id of the user to be deleted
     */
    public void deleteUser(String id) {
        User.findById(id).delete();
    }

    /**
     * Hashes the given password using the SHA-256 algorithm.
     * @param password the password to be hashed
     * @return the hashed password as a Base64-encoded string
     */
    private String hashOfPassword(String password) {
        try {
            MessageDigest mda = MessageDigest.getInstance("SHA-256");
            byte[] hash = mda.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error hashing password", e);
        }
        return null;
    }

    /**
     * Verifies that the password given is equal to the userPassword
     * @param userName username given of the user
     * @param password password given of the user
     * @return True if it is the same ot False if not
     */
    public boolean verifyPassword(String userName, String password) {
        User user = User.find("userName", userName).firstResult();
        if (user != null) {
            String userPassword =  user.getHashedPassword();
            return userPassword.equals(hashOfPassword(password));
        }
        return false;
    }
}
