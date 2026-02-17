package user;

/**
 * Custom exception class used to signal invalid user-related operations.
 * 
 * This exception is typically thrown in the following scenarios:
 * -Attempting to register a user with an already existing username, or without choosing an avatar.
 * -Logging in with an incorrect username or password
 * -Loggin or registring with with empty text.
 * 
 */
public class InvalidUserException extends Exception {

    /**
     * Constructs a new InvalidUserException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidUserException(String message) {
        super(message);
    }
}
