package a2;

/**
 * An exception that indicates that an action is formatted incorrectly.
 */
public class ActionFormatException extends Exception {
    public ActionFormatException() {
    }

    public ActionFormatException(String message) {
        super(message);
    }
}
