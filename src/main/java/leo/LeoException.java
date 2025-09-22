package leo;

/**
 * Checked exception for user, input, or storage errors in Leo.
 */
public class LeoException extends Exception {
    /**
     * Creates a new LeoException with the specified message.
     *
     * @param message the detail message
     */
    public LeoException(String message) {
        super(message);
    }
}
