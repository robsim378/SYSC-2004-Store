//Robert Simionescu
//101143542

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Class used to manage an inventory and shopping carts.
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class StoreManager {

    /**
     * Inner class pairing a ShoppingCart with its cartID. Cart not in use if cartID is -1.
     */
    private class ShoppingCartWithID {

        private ShoppingCart cart;
        private int cartID;

        /**
         * Constructor for the ShoppingCartWithID.
         * @param cart The cart to be paired with the given ID.
         * @param cartID The ID to be assigned to the cart.
         */
        public ShoppingCartWithID(ShoppingCart cart, int cartID) {
            this.cart = cart;
            this.cartID = cartID;
        }

        /**
         * Getter for the cart.
         * @return The cart stored as a ShoppingCart.
         */
        public ShoppingCart getCart() {
            return cart;
        }

        /**
         * Getter for the cartID.
         * @return The cartID stored as an int.
         */
        public int getcartID() {
            return cartID;
        }

        /**
         * Setter for the cart.
         * @param cart The ShoppingCart to be assigned to cart.
         */
        public void setCart(ShoppingCart cart) {
            this.cart = cart;
        }

        public void addToCart(int productID, int quantity) {
            cart.addProduct(productID, quantity);
        }

        public void setCartID(int cartID) {
            this.cartID = cartID;
        }
    }

    /**
     * The inventory of the store.
     */
    private Inventory inventory;

    /**
     * The list of carts paired with their IDs.
     */
    private ArrayList<ShoppingCartWithID> shoppingCarts;

    /**
     * Constructor for StoreManager.
     */
    public StoreManager() {
        inventory = new Inventory();
        shoppingCarts = new ArrayList<ShoppingCartWithID>();
    }

    /**
     * Constructor for StoreManager used during testing. If this is still here when I submit, please ignore it.
     */
    public StoreManager(Inventory inventory) {
        this.inventory = inventory;
        shoppingCarts = new ArrayList<ShoppingCartWithID>();
    }

    /**
     * Returns a list of the IDs of all the products in the inventory.
     * @return an ArrayList of Integers containing all the products in the inventory.
     */
    public ArrayList<Integer> getInventory() {
        return inventory.getInventory();
    }

    /**
     * Getter for the stock of the item in the inventory with the given ID.
     * @param id The ID of the product to get the stock of.
     * @return The remaining stock stored as an int.
     */
    public int getStock(int id) {
        return(inventory.getStock(id));
    }

    /**
     * Getter for the name of a product given its product ID.
     * @param productID The ID of the product.
     * @return The name of the product stored as a String.
     */
    public String getProductName(int productID) {
        return inventory.getName(productID);
    }

    /**
     * Getter for the price of a product given its product ID.
     * @param productID The ID of the product.
     * @return The price of the product stored as a float.
     */
    public float getProductPrice(int productID) {
        return inventory.getPrice(productID);
    }

    /**
     * Getter for the stock of a product given its product ID.
     * @param productID The ID of the product.
     * @return The price of hte product stored as an int.
     */
    public int getProductStock(int productID) {
        return inventory.getStock(productID);
    }

    /**
     * Getter for a cart given its cartID.
     * @param cartID The cartID of the cart to return.
     * @return The cart with the given ID.
     */
    public ShoppingCart getCart(int cartID) {
        return shoppingCarts.get(cartID).getCart();
    }

    /**
     * Creates a new ShoppingCart and returns its cartID.
     * @return the cartID of the new ShoppingCart.
     */
    public int createCart() {
        /*
        Finds the lowest integer not currently in use as a cart ID and adds a ShoppingCartWithID with that ID to
        shoppingCarts.
         */
        int i;
        for (i = 0; i < shoppingCarts.size(); i++) {
            if (shoppingCarts.get(i).getcartID() == -1) {
                shoppingCarts.set(i, new ShoppingCartWithID(new ShoppingCart(), i));
                return i;
            }
        }

        shoppingCarts.add(i, new ShoppingCartWithID(new ShoppingCart(), i));
        return i;
    }

    /**
     * Removes the requested shopping cart from the list of shopping carts in use.
     * @param cartID The ID of the cart to be removed.
     */
    public void removeCart(int cartID) {
        /*
        If removing the final cart in the shoppingCarts ArrayList, remove all unused carts between it and the cart with
        the highest ID in use.
         */
        if (cartID == shoppingCarts.size() - 1) {
            int i = shoppingCarts.size() - 1;
            shoppingCarts.remove(i);

            i--;
            while (i >= 0 && shoppingCarts.get(i).cartID == -1) {
                shoppingCarts.remove(i);
                i--;
            }
        }
        else {
            shoppingCarts.get(cartID).setCartID(-1);
        }
    }

    /**
     * Attempts to add the requested quantity of the selected product in the given cart.
     * @param cartID The ID of the cart to add the product to.
     * @param productID The ID of the product to be added to the cart.
     * @param quantity The amount of the product to add to the cart and subtract from the inventory.
     */
    public void addToCart(int cartID, int productID, int quantity) {
        if (inventory.getStock(productID) >= quantity) {
            shoppingCarts.get(cartID).addToCart(productID, quantity);
            inventory.removeStock(productID, quantity);
        }
        else {
            throw new InvalidParameterException("Not enough stock of product with ID " + productID + " to add desired amount to cart.");
        }
    }

    /**
     * Removes the requested product from the specified cart and returns the quantity from the cart to the inventory.
     * @param cartID The ID of the cart to remove a product from.
     * @param productID The product to remove from the cart.
     */
    public void removeFromCart(int cartID, int productID) {
        inventory.addStock(productID, shoppingCarts.get(cartID).cart.getQuantity(productID));
        shoppingCarts.get(cartID).cart.removeProduct(productID);
    }

    /**
     * Sets the quantity of the given product in the cart to the selected new quantity if possible. If lowering the
     * quantity in the cart, returns the removed products to the inventory. If increasing, checks if there is enough
     * stock.
     * @param cartID The ID of the cart to set the quantity of a product in
     * @param productID The ID of the product to change the quantity of in the
     * @param newQuantity The new quantity for the product.
     */
    public void setQuantityInCart(int cartID, int productID, int newQuantity) {
        if (newQuantity <= 0) {
            throw new InvalidParameterException("New quantity must be a positive integer. To remove an item from the " +
                    "cart, use removeFromCart().");
        }
        if (newQuantity > shoppingCarts.get(cartID).cart.getQuantity(productID)) {
            if (inventory.getStock(productID) >= newQuantity - shoppingCarts.get(cartID).cart.getQuantity(productID)) {
                inventory.removeStock(productID, newQuantity - shoppingCarts.get(cartID).cart.getQuantity(productID));
                shoppingCarts.get(cartID).cart.setQuantity(productID, newQuantity);
            }
            else {
                throw new InvalidParameterException("Not enough stock of product with ID " + productID + " to add desired amount to cart.");
            }
        }
        else if (newQuantity < shoppingCarts.get(cartID).cart.getQuantity(productID)) {
            inventory.addStock(productID, shoppingCarts.get(cartID).cart.getQuantity(productID) - newQuantity);
            shoppingCarts.get(cartID).cart.setQuantity(productID, newQuantity);
        }
    }

    /**
     * Removes all items from a cart and returns them to the inventory, then removes the cart from the list of carts
     * currently in use. Use if the user quits without checking out.
     * @param cartID The cartID of the cart to empty.
     */
    public void emptyCart(int cartID) {
        // Checks if the cart is empty. If not, removes the first element and checks again.
        while (shoppingCarts.get(cartID).cart.getCart().size() != 0) {
            removeFromCart(cartID, shoppingCarts.get(cartID).cart.getCart().get(0)[0]);
        }
    }

    /**
     * Calculates the total cost of all the items in the cart and removes the cart from the list of carts currently in
     * use.
     * @param cartID The ID of the cart.
     * @return The total cost of all the items in the cart.
     */
    public double processTransaction(int cartID) {
        double totalCost = 0;
        for (int[] product : shoppingCarts.get(cartID).cart.getCart()) {
            totalCost += inventory.getPrice(product[0]) * product[1];
        }
        removeCart(cartID);
        return Math.round(100.0 * totalCost) / 100.0;
    }
}
