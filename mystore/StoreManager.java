//Robert Simionescu
//101143542

package mystore;

import storeexceptions.InvalidIDException;
import storeexceptions.NegativeStockException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Class used to manage an inventory and shopping carts.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class StoreManager {

    /**
     * Inner class pairing a Store.ShoppingCart with its cartID. Cart not in use if cartID is -1.
     */
    private class ShoppingCartWithID {

        private ShoppingCart cart;
        private int cartID;

        /**
         * Constructor for the ShoppingCartWithID.
         *
         * @param cart   The cart to be paired with the given ID.
         * @param cartID The ID to be assigned to the cart.
         */
        public ShoppingCartWithID(ShoppingCart cart, int cartID) {
            this.cart = cart;
            this.cartID = cartID;
        }

        /**
         * Getter for the cart.
         *
         * @return The cart stored as a Store.ShoppingCart.
         */
        public ShoppingCart getCart() {
            return cart;
        }

        /**
         * Getter for the cartID.
         *
         * @return The cartID stored as an int.
         */
        public int getCartID() {
            return cartID;
        }

        /**
         * Setter for the cart.
         *
         * @param cart The Store.ShoppingCart to be assigned to cart.
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
     * Constructor for Store.StoreManager.
     */
    public StoreManager() {
        inventory = new Inventory();
        shoppingCarts = new ArrayList<ShoppingCartWithID>();
    }

    /**
     * Constructor for Store.StoreManager that takes an inventory for the store.
     */
    public StoreManager(Inventory inventory) {
        this.inventory = inventory;
        shoppingCarts = new ArrayList<ShoppingCartWithID>();
    }

    /**
     * Returns a list of the IDs of all the products in the inventory.
     *
     * @return an ArrayList of Integers containing all the products in the inventory.
     */
    public ArrayList<Integer> getInventory() {
        return inventory.getInventory();
    }

    /**
     * Getter for the name of a product given its product ID.
     *
     * @param productID The ID of the product.
     * @return The name of the product stored as a String.
     */
    public String getProductName(int productID) {
        return inventory.getName(productID);
    }

    /**
     * Getter for the price of a product given its product ID.
     *
     * @param productID The ID of the product.
     * @return The price of the product stored as a float.
     */
    public float getProductPrice(int productID) {
        return inventory.getPrice(productID);
    }

    /**
     * Getter for the stock of a product given its product ID.
     *
     * @param productID The ID of the product.
     * @return The price of hte product stored as an int.
     */
    public int getProductStock(int productID) {
        return inventory.getStock(productID);
    }

    /**
     * Getter for a cart given its cartID.
     *
     * @param cartID The cartID of the cart to return.
     * @return The cart with the given ID.
     */
    public ShoppingCart getCart(int cartID) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            return shoppingCarts.get(cartID).getCart();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    /**
     * Creates a new Store.ShoppingCart and returns its cartID.
     *
     * @return the cartID of the new Store.ShoppingCart.
     */
    public int createCart() {
        /*
        Finds the lowest integer not currently in use as a cart ID and adds a ShoppingCartWithID with that ID to
        shoppingCarts.
         */
        int i;
        for (i = 0; i < shoppingCarts.size(); i++) {
            if (shoppingCarts.get(i).getCartID() == -1) {
                shoppingCarts.set(i, new ShoppingCartWithID(new ShoppingCart(), i));
                return i;
            }
        }

        shoppingCarts.add(i, new ShoppingCartWithID(new ShoppingCart(), i));
        return i;
    }

    /**
     * Removes the requested shopping cart from the list of shopping carts in use.
     *
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
        } else {
            shoppingCarts.get(cartID).setCartID(-1);
        }
    }

    /**
     * Attempts to add the requested quantity of the selected product in the given cart.
     *
     * @param cartID    The ID of the cart to add the product to.
     * @param productID The ID of the product to be added to the cart.
     * @param quantity  The amount of the product to add to the cart and subtract from the inventory.
     */
    public void addToCart(int cartID, int productID, int quantity) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            shoppingCarts.get(cartID).addToCart(productID, quantity);
            inventory.removeStock(productID, quantity);

        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    /**
     * Removes the requested product from the specified cart and returns the quantity from the cart to the inventory.
     *
     * @param cartID    The ID of the cart to remove a product from.
     * @param productID The product to remove from the cart.
     */
    public void removeFromCart(int cartID, int productID) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            inventory.addStock(productID, shoppingCarts.get(cartID).cart.getQuantity(productID));
            shoppingCarts.get(cartID).cart.removeProduct(productID);

        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    /**
     * Sets the quantity of the given product in the cart to the selected new quantity if possible. If lowering the
     * quantity in the cart, returns the removed products to the inventory. If increasing, checks if there is enough
     * stock.
     *
     * @param cartID      The ID of the cart to set the quantity of a product in
     * @param productID   The ID of the product to change the quantity of in the
     * @param newQuantity The new quantity for the product.
     */
    public void setQuantityInCart(int cartID, int productID, int newQuantity) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            if (newQuantity < 0) {
                throw new InvalidParameterException("New quantity must be a positive integer.");
            } else if (newQuantity == 0) {
                throw new InvalidParameterException("New quantity must be a positive integer. To remove an item from the " +
                        "cart, use removeFromCart().");
            }
            if (newQuantity > shoppingCarts.get(cartID).cart.getQuantity(productID)) {
                if (inventory.getStock(productID) >= newQuantity - shoppingCarts.get(cartID).cart.getQuantity(productID)) {
                    inventory.removeStock(productID, newQuantity - shoppingCarts.get(cartID).cart.getQuantity(productID));
                    shoppingCarts.get(cartID).cart.setQuantity(productID, newQuantity);
                } else {
                    throw new NegativeStockException("Not enough stock of product with ID " + productID + " to add desired amount to cart.");
                }
            } else if (newQuantity < shoppingCarts.get(cartID).cart.getQuantity(productID)) {
                inventory.addStock(productID, shoppingCarts.get(cartID).cart.getQuantity(productID) - newQuantity);
                shoppingCarts.get(cartID).cart.setQuantity(productID, newQuantity);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    /**
     * Returns the quantity of a specific product in the chosen cart.
     * @param cartID The ID of the cart to get the quantity from.
     * @param productID The ID of the product to get the quantity of.
     * @return
     */
    public int getQuantityInCart(int cartID, int productID) {
        return shoppingCarts.get(cartID).getCart().getQuantity(productID);
    }

    /**
     * Removes all items from a cart and returns them to the inventory, then removes the cart from the list of carts
     * currently in use. Use if the user quits without checking out.
     *
     * @param cartID The cartID of the cart to empty.
     */
    public void emptyCart(int cartID) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            // Checks if the cart is empty. If not, removes the first element and checks again.
            while (shoppingCarts.get(cartID).cart.getCart().size() != 0) {
                removeFromCart(cartID, shoppingCarts.get(cartID).cart.getCart().get(0)[0]);
            }
            removeCart(cartID);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    public double getCartTotal(int cartID) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            double totalCost = 0;
            for (int[] product : shoppingCarts.get(cartID).cart.getCart()) {
                totalCost += inventory.getPrice(product[0]) * product[1];
            }
            return Math.round(100.0 * totalCost) / 100.0;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }
    }

    /**
     * Calculates the total cost of all the items in the cart and removes the cart from the list of carts currently in
     * use.
     *
     * @param cartID The ID of the cart.
     * @return The total cost of all the items in the cart.
     */
    public double processTransaction(int cartID) {
        try {
            if (shoppingCarts.get(cartID).getCartID() == -1) {
                throw new InvalidIDException("No cart exists with ID " + cartID + ".");
            }
            double totalCost = 0;
            for (int[] product : shoppingCarts.get(cartID).cart.getCart()) {
                totalCost += inventory.getPrice(product[0]) * product[1];
            }
            removeCart(cartID);
            return Math.round(100.0 * totalCost) / 100.0;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIDException("No cart exists with ID " + cartID + ".");
        }

    }
}
