//Robert Simionescu
//101143542

public class Product {
    private final String NAME;      //Specification says that these attributes cannot be changed once set, so they are final
    private final int ID;
    private final float PRICE;      //float was used instead of double since the extra precision is not needed

    public Product(String name, int id, float price) {
        /* Constructor for the product. Assigns the name, id, and price to the corresponding attributes. Price is
        rounded to 2 decimal places since smaller denominations of Canadian currency than one cent do not exist.
         */
        this.NAME = name;
        this.ID = id;
        this.PRICE = Math.round(100.0 * price) / (float) 100.0;     //Rounds price to 2 decimal places
    }

    public String getName() {
        return NAME;
    }

    public int getId() {
        return ID;
    }

    public float getPrice() {
        return PRICE;
    }
}
