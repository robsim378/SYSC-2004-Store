//Robert Simionescu
//101143542

package storeexceptions;

/**
 * Custom exception used when the ID of a product is not valid for any reason.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class InvalidIDException extends RuntimeException {
    public InvalidIDException(String message) {
        super(message);
    }
}