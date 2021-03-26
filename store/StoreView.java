//Robert Simionescu
//101143542

package store;

import storeexceptions.InvalidIDException;
import storeexceptions.NegativeStockException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * GUI manager for the store.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class StoreView {
    private final StoreManager store;
    private final int cartID;

    /**
     * Constructor for a Store.StoreView. Takes a Store.StoreManager and creates a new cart for use within that Store.StoreManager.
     *
     * @param store The Store.StoreManager for the store the user is trying to use.
     */
    StoreView(StoreManager store) {
        this.store = store;
        cartID = store.createCart();
    }

    public static void main(String[] args) {

        Inventory i1 = new Inventory();

        i1.addProduct("GTX 3070", 5692, 799.994f, 0);
        i1.addProduct("GTX 3080", 5691, 899.989f, 1);
        i1.addProduct("GTX 3090", 9, 999.655f, 2);
        i1.addProduct("GTX 3060", 3567, 599.832576f, 4567);
        i1.addProduct("GTX 2060", 3566, 400.00f, 8);

        StoreManager sm0 = new StoreManager(i1);
        StoreView store0 = new StoreView(sm0);

        store0.viewStore();

    }

    /**
     * Prints the cart to the console.
     */
    private String displayCart() {
        System.out.println();
        System.out.println("|----------CART----------|");
        System.out.println("Product ID | Product Name | Price | Quantity");

        for (int i = 0; i < store.getCart(cartID).getCart().size(); i++) {
            System.out.print(store.getCart(cartID).getCart().get(i)[0]);
            System.out.print(" | ");
            System.out.print(store.getProductName(store.getCart(cartID).getCart().get(i)[0]));
            System.out.print(" | ");
            System.out.print(store.getProductPrice(store.getCart(cartID).getCart().get(i)[0]));
            System.out.print(" | ");
            System.out.println(store.getCart(cartID).getCart().get(i)[1]);
        }

        String nextState = "";
        int product;
        int quantity;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> inventory = store.getInventory();

        System.out.println();
        System.out.print("Enter a command: ");
        nextState = scanner.nextLine();

        while (true) {
            switch (nextState.toUpperCase()) {
                case "PRODUCTS":
                    return "PRODUCTS";
                case "REMOVE FROM CART":
                    System.out.println();
                    System.out.print("Enter ID of product to remove from cart: ");
                    try {
                        product = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Type 'Help' for a list of commands.");
                        break;
                    }

                    try {
                        store.removeFromCart(cartID, product);
                    } catch (InvalidIDException e) {
                        if (!inventory.contains(product)) {
                            System.out.println("No product exists with ID " + product + ".");
                            break;
                        }
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println();
                    System.out.print("Removed " + store.getProductName(product) + " from cart.");

                    break;
                case "CHANGE QUANTITY":
                    System.out.println();
                    System.out.print("Enter ID of product to change quantity: ");
                    try {
                        product = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Type 'Help' for a list of commands.");
                        break;
                    }

                    System.out.print("Enter new quantity of product: ");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Type 'Help' for a list of commands.");
                        break;
                    }

                    try {
                        store.setQuantityInCart(cartID, product, quantity);
                    } catch (InvalidIDException e) {
                        if (!inventory.contains(product)) {
                            System.out.println("No product exists with ID " + product + ".");
                            break;
                        }
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CART":
                    return "CART";
                case "CHECKOUT":
                    checkout();
                    return "CHECKOUT";
                case "QUIT":
                    quit();
                    return "QUIT";
                case "HELP":
                    System.out.println();
                    System.out.println("Products: Lists all products available in the store.");
                    System.out.println("Remove from cart: Removes a product from your cart.");
                    System.out.println("Change quantity: Change the quantity of a product in your cart.");
                    System.out.println("Cart: Lists all products currently in your cart.");
                    System.out.println("Checkout: Checks you out of the store and displays your total.");
                    System.out.println("Quit: Closes the store.");
                    break;
                default:
                    System.out.println("Invalid input. Type 'Help' for a list of commands.");
            }
            System.out.println();
            System.out.print("Enter a command: ");
            nextState = scanner.nextLine();
        }
    }

    /**
     * Prints all the products in the inventory to the console.
     */
    private String displayProducts() {
        System.out.println();
        System.out.println("|----------Products----------|");
        System.out.println("Product ID | Product Name | Price | Stock");
        ArrayList<Integer> inventory = store.getInventory();

        for (Integer productID : inventory) {
            System.out.print(productID);
            System.out.print(" | ");
            System.out.print(store.getProductName(productID));
            System.out.print(" | ");
            System.out.print(store.getProductPrice(productID));
            System.out.print(" | ");
            System.out.println(store.getProductStock(productID));
        }

        String nextState = "";
        int product;
        int quantity;
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.print("Enter a command: ");

        nextState = scanner.nextLine();


        while (true) {
            switch (nextState.toUpperCase()) {
                case "PRODUCTS":
                    return "PRODUCTS";
                case "ADD TO CART":
                    System.out.println();
                    System.out.print("Enter ID of product to add: ");
                    try {
                        product = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Type 'Help' for a list of commands.");
                        break;
                    }


                    System.out.println();

                    System.out.print("Enter quantity of product to add: ");

                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Type 'Help' for a list of commands.");
                        break;
                    }

                    try {
                        store.addToCart(cartID, product, quantity);
                    } catch (InvalidIDException e) {
                        System.out.println(e.getMessage());
                        break;
                    } catch (NegativeStockException e) {
                        System.out.println("Not enough of that product in stock.");
                        break;
                    }

                    System.out.println();

                    System.out.print("Added " + quantity + " " + store.getProductName(product));
                    if (quantity == 1) {
                        System.out.println(" to cart.");
                    } else {
                        System.out.println("s to cart.");
                    }
                    break;
                case "CART":
                    return "CART";
                case "CHECKOUT":
                    checkout();
                    return "CHECKOUT";
                case "QUIT":
                    quit();
                    return "QUIT";
                case "HELP":
                    System.out.println();
                    System.out.println("Products: Lists all products available in the store.");
                    System.out.println("Add to cart: Adds a product to your cart.");
                    System.out.println("Cart: Lists all products currently in your cart.");
                    System.out.println("Checkout: Checks you out of the store and displays your total.");
                    System.out.println("Quit: Closes the store.");
                    break;
                default:
                    System.out.println("Invalid input. Type 'Help' for a list of commands.");
            }
            System.out.println();
            System.out.print("Enter a command: ");
            nextState = scanner.nextLine();
        }
    }

    /**
     * Returns all items from the cart to the inventory and removes the cart from the list of carts in use.
     */
    private void quit() {
        store.emptyCart(cartID);
    }

    /**
     * Prints the total cost of items in the cart to the console and removes the cart from the list of carts in use.
     */
    private void checkout() {
        System.out.println("Total cost of items in cart: $" + store.processTransaction(cartID));
    }

    public void viewStore() {
        String nextState = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to [STORE NAME]");
        System.out.println("Type 'Help' for a list of commands.");
        System.out.print("Enter a command: ");

        nextState = scanner.nextLine();

        do {
            switch (nextState.toUpperCase()) {
                case "PRODUCTS":
                    nextState = displayProducts();
                    break;
                case "CART":
                    nextState = displayCart();
                    break;
                case "CHECKOUT":
                    checkout();
                    break;
                case "QUIT":
                    quit();
                    break;
                case "HELP":
                    System.out.println();
                    System.out.println("Products: Lists all products available in the store.");
                    System.out.println("Cart: Lists all products currently in your cart.");
                    System.out.println("Checkout: Checks you out of the store and displays your total.");
                    System.out.println("Quit: Closes the store.");
                    System.out.println();

                    System.out.print("Enter a command: ");
                    nextState = scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid input. Type 'Help' for a list of commands.");
                    nextState = scanner.nextLine();
            }
        } while (!nextState.equalsIgnoreCase("QUIT") && !nextState.equalsIgnoreCase("CHECKOUT"));
    }
}
