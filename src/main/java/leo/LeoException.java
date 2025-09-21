package leo;

/**
 * Checked exception for user, input, or storage errors in Leo.
 */
public class LeoException extends Exception {
    public LeoException(String message) {
        super(message);
    }
}
