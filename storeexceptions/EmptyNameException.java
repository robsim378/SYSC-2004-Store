//Robert Simionescu
//101143542

package storeexceptions;

/**
 * Custom exception used when a product has an empty name.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class EmptyNameException extends RuntimeException {
    public EmptyNameException(String message) {
        super(message);
    }
}
