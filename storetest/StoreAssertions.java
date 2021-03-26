// Robert Simionescu
// 101143542

package storetest;

import org.junit.jupiter.api.Assertions;
import store.Inventory;
import store.Product;
import store.ShoppingCart;

/**
 * Custom assertions used to streamline testing by not having to write several lines of assertions for each test.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public final class StoreAssertions {
    private StoreAssertions() {
    }

    /**
     * Asserts that two Products are identical.
     *
     * @param product  One of the products to be compared.
     * @param expected The second product to be compared.
     */
    public static void assertProductEquals(Product product, Product expected) {
        Assertions.assertEquals(product.getId(), expected.getId());
        Assertions.assertEquals(product.getName(), expected.getName());
        Assertions.assertEquals(product.getPrice(), expected.getPrice());
    }

    /**
     * Asserts that two Inventories are identical.
     *
     * @param inventory One of the Inventories to be compared.
     * @param expected  The second Inventory to be compared.
     */
    public static void assertInventoryEquals(Inventory inventory, Inventory expected) {
        Assertions.assertEquals(inventory.getInventory(), expected.getInventory());

        // Compares the stocks of products in the two inventories one by one and asserts that they are equal.
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            Assertions.assertEquals(inventory.getStock(inventory.getInventory().get(i)), expected.getStock(expected.getInventory().get(i)));
        }
    }

    /**
     * Asserts that two ShoppingCarts are identical.
     *
     * @param cart     One of the ShoppingCarts to be compared.
     * @param expected The second ShoppingCart to be compared.
     */
    public static void assertCartEquals(ShoppingCart cart, ShoppingCart expected) {
        Assertions.assertEquals(cart.getCart(), expected.getCart());
    }
}
