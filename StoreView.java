//Robert Simionescu
//101143542

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * GUI manager for the store.
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class StoreView {
    private StoreManager store;
    private int cartID;

    /**
     * Constructor for a StoreView. Takes a StoreManager and creates a new cart for use within that StoreManager.
     * @param store The StoreManager for the store the user is trying to use.
     */
    StoreView(StoreManager store) {
        this.store = store;
        cartID = store.createCart();
    }

    /**
     * Prints the cart to the console.
     */
    private String displayCart() {
        System.out.println("");
        System.out.println("|----------CART----------|");
        System.out.println("Product ID | Product Name | Price| Quantity");

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

        System.out.println("");
        System.out.print("Enter a command: ");
        nextState = scanner.nextLine();

        while (true) {
            switch(nextState.toUpperCase()) {
                case "PRODUCTS":
                    return "PRODUCTS";
                case "REMOVE FROM CART":
                    System.out.println("");
                    System.out.print("Enter ID of product to remove: ");
                    product = Integer.parseInt(scanner.nextLine());
                    if (store.getCart(cartID).getQuantity(product) == 0) {
                        System.out.println("No product with that ID in cart.");
                        break;
                    }
                    if (!inventory.contains(product)) {
                        System.out.println("No product exists with that ID.");
                        break;
                    }

                    store.removeFromCart(cartID, product);

                    System.out.println("");
                    System.out.print("Removed " + store.getProductName(product) + " from cart.");

                    break;
                case "CHANGE QUANTITY":
                    System.out.println("");
                    System.out.print("Enter ID of product to remove: ");
                    product = Integer.parseInt(scanner.nextLine());
                    if (store.getCart(cartID).getQuantity(product) == 0) {
                        System.out.println("No product with that ID in cart.");
                        break;
                    }
                    if (!inventory.contains(product)) {
                        System.out.println("No product exists with that ID.");
                        break;
                    }

                    System.out.print("Enter new quantity of product: ");

                    quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity < 0) {
                        System.out.println("New quantity must be a positive integer.");
                    }
                    try {
                        store.setQuantityInCart(cartID, product, quantity);
                    } catch(InvalidParameterException e) {
                        System.out.println("Not enough stock of product to set this quantity.");
                    }

                case "CART":
                    return "CART";
                case "CHECKOUT":
                    checkout();
                    return "CHECKOUT";
                case "QUIT":
                    quit();
                    return "QUIT";
                case "HELP":
                    System.out.println("");
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
            System.out.println("");
            System.out.print("Enter a command: ");
            nextState = scanner.nextLine();
        }
    }

    /**
     * Prints all the products in the inventory to the console.
     */
    private String displayProducts() {
        System.out.println("");
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

        System.out.println("");
        System.out.print("Enter a command: ");

        nextState = scanner.nextLine();


        while (true) {
            switch(nextState.toUpperCase()) {
                case "PRODUCTS":
                    return "PRODUCTS";
                case "ADD TO CART":
                    System.out.println("");
                    System.out.print("Enter ID of product to add: ");
                    product = Integer.parseInt(scanner.nextLine());
                    if (!inventory.contains(product)) {
                        System.out.println("No product exists with that ID.");
                        break;
                    }

                    System.out.println("");

                    System.out.print("Enter quantity of product to add: ");
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (store.getStock(product) < quantity) {
                        System.out.println("Not enough of that product in stock.");
                        break;
                    }

                    store.addToCart(cartID,product,quantity);

                    System.out.println("");

                    System.out.print("Added " + quantity + " " + store.getProductName(product));
                    if (quantity == 1) {
                        System.out.println(" to cart.");
                    }
                    else {
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
                    System.out.println("");
                    System.out.println("Products: Lists all products available in the store.");
                    System.out.println("Add to cart: Adds a product to your cart.");
                    System.out.println("Cart: Lists all products currently in your cart.");
                    System.out.println("Checkout: Checks you out of the store and displays your total.");
                    System.out.println("Quit: Closes the store.");
                    break;
                default:
                    System.out.println("Invalid input. Type 'Help' for a list of commands.");
            }
            System.out.println("");
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
            switch(nextState.toUpperCase()) {
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
                    System.out.println("");
                    System.out.println("Products: Lists all products available in the store.");
                    System.out.println("Cart: Lists all products currently in your cart.");
                    System.out.println("Checkout: Checks you out of the store and displays your total.");
                    System.out.println("Quit: Closes the store.");
                    System.out.println("");

                    System.out.print("Enter a command: ");
                    nextState = scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid input. Type 'Help' for a list of commands.");
                    nextState = scanner.nextLine();
            }
        } while (!nextState.toUpperCase().equals("QUIT") && !nextState.toUpperCase().equals("CHECKOUT"));
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
}
