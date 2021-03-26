//Robert Simionescu
//101143542

package storeexceptions;

/**
 * Custom exception used when the stock of a product is negative.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class NegativeStockException extends RuntimeException {
    public NegativeStockException(String message) {
        super(message);
    }
}
