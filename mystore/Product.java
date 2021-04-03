//Robert Simionescu
//101143542

package mystore;

/**
 * Class representing a product.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class Product {
    private final String NAME;      //Specification says that these attributes cannot be changed once set, so they are final
    private final int ID;
    private final float PRICE;      //float was used instead of double since the extra precision is not needed

    /**
     * Constructor for a Store.Product. Takes a name, ID number, and price.
     *
     * @param name  The name of the product stored as a String.
     * @param id    The ID of the product stored as an int.
     * @param price The price of the product stored as a double.
     */
    public Product(String name, int id, float price) {
        this.NAME = name;
        this.ID = id;
        this.PRICE = Math.round(100.0 * price) / (float) 100.0;     //Rounds price to 2 decimal places
    }

    /**
     * Getter for the name of the product.
     *
     * @return The name of the product stored as a String.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Getter for the product ID.
     *
     * @return the ID of the product stored as an int.
     */
    public int getId() {
        return ID;
    }

    /**
     * The price of the product in dollars.
     *
     * @return The price of the product stored as a double.
     */
    public float getPrice() {
        return PRICE;
    }
}
