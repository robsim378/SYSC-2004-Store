//Robert Simionescu
//101143542

package storeexceptions;

/**
 * Custom exception used when the price of a product is negative.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class NegativePriceException extends RuntimeException {
    public NegativePriceException(String message) {
        super(message);
    }
}
