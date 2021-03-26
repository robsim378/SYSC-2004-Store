//Robert Simionescu
//101143542

package store;

import storeexceptions.InvalidIDException;

import java.util.ArrayList;

/**
 * Class defining a shopping cart containing the products that the user has added to their carts and the quantity of
 * each product in the cart.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class ShoppingCart {
    /**
     * ArrayList of integer arrays. Each array has length 2, with index 0 being the product ID and index 1 being the
     * quantity in the cart.
     */
    private final ArrayList<int[]> cart;

    /**
     * Constructor for Store.ShoppingCart.
     */
    public ShoppingCart() {
        cart = new ArrayList<int[]>();
    }

    /**
     * getter for the contents of the cart.
     *
     * @return The contents of the cart stored as an ArrayList of integer arrays. Index 0 in each array is the product
     * ID and index 1 is the quantity of the product in the cart.
     */
    public ArrayList<int[]> getCart() {
        return cart;
    }

    /**
     * Adds a product to the cart with the given quantity.
     *
     * @param id       The ID of the product to be added to the cart.
     * @param quantity The amount of the product to be added to the cart.
     */
    public void addProduct(int id, int quantity) {
        for (int[] product : cart) {
            if (product[0] == id) {
                throw new InvalidIDException("Cart already contains product with ID " + id + ".");
            }
        }
        cart.add(new int[]{id, quantity});
    }

    /**
     * removes a product from the cart.
     *
     * @param id The ID of the product to be removed from the cart.
     */
    public void removeProduct(int id) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i)[0] == id) {
                cart.remove(i);
                return;
            }
        }
        throw new InvalidIDException("No product with ID " + id + " in cart.");
    }

    /**
     * Sets a new quantity for a given product in the cart.
     *
     * @param id          The ID of the product to change the quantity of.
     * @param newQuantity The new quantity of the product in the cart.
     */
    public void setQuantity(int id, int newQuantity) {
        if (newQuantity == 0) {
            removeProduct(id);
        }
        for (int[] product : cart) {
            if (product[0] == id) {
                product[1] = newQuantity;
                return;
            }
        }
        throw new InvalidIDException("No product with ID " + id + " in cart.");
    }

    /**
     * Gets the amount of a given product in the cart.
     *
     * @param id The ID of the product to get the amount in the cart.
     * @return The amount of the product with the given ID number in the cart.
     */
    public int getQuantity(int id) {
        for (int[] product : cart) {
            if (product[0] == id) {
                return product[1];
            }
        }
        return 0;
    }
}