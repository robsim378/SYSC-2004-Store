// Robert Simionescu
// 101143542

package storetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.Inventory;
import store.ShoppingCart;
import store.StoreManager;
import storeexceptions.InvalidIDException;
import storeexceptions.NegativeStockException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Unit tests for StoreManager.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class TestStoreManager {
    StoreManager emptyStore;
    StoreManager regularStore;
    Inventory regularInventory;
    int cartID0;

    @BeforeEach
    public void init() {
        regularInventory = new Inventory();
        regularInventory.addProduct("no stock product", 5692, 799.994f, 0);
        regularInventory.addProduct("1 stock product", 5691, 899.989f, 1);
        regularInventory.addProduct("ID 0 product", 0, 999.655f, 2);
        regularInventory.addProduct("regular product", 3567, 599.832576f, 4567);

        emptyStore = new StoreManager();
        regularStore = new StoreManager(regularInventory);
        cartID0 = regularStore.createCart();
    }

    /**
     * Tests getInventory on a store with an empty inventory.
     */
    @Test
    public void testGetInventory0() {
        Assertions.assertEquals(emptyStore.getInventory(), new ArrayList<Integer>());
    }

    /**
     * Tests getInventory on a store with a regular inventory.
     */
    @Test
    public void testGetInventory1() {
        ArrayList<Integer> expectedInventory = new ArrayList<Integer>();
        expectedInventory.add(0);
        expectedInventory.add(3567);
        expectedInventory.add(5691);
        expectedInventory.add(5692);

        Assertions.assertEquals(regularStore.getInventory(), expectedInventory);
    }

    /**
     * Tests getProductName with a valid product ID.
     */
    @Test
    public void testGetProductName0() {
        Assertions.assertEquals(regularStore.getProductName(5691), "1 stock product");
    }

    /**
     * Tests getProductName with a product ID that is already in use.
     */
    @Test
    public void testGetProductName1() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getProductName(8));
    }

    /**
     * Tests getProductPrice with a valid product ID.
     */
    @Test
    public void testGetProductPrice0() {
        Assertions.assertEquals(regularStore.getProductPrice(5691), 899.989f, 0.01);
    }

    /**
     * Tests getProductPrice with a product ID that is already in use.
     */
    @Test
    public void testGetProductPrice1() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getProductPrice(8));
    }

    /**
     * Tests getProductStock with a valid product ID.
     */
    @Test
    public void testGetProductStock0() {
        Assertions.assertEquals(regularStore.getProductStock(5691), 1);
    }

    /**
     * Tests getProductStock with a product ID that is already in use.
     */
    @Test
    public void testGetProductStock1() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getProductStock(8));
    }

    /**
     * Tests createCart. Also tests getCart.
     */
    @Test
    public void testCreateCart0() {
        // Asserts that attempting to access a cart that does not exist throws an exception.
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0 + 1));
        Assertions.assertEquals(regularStore.createCart(), cartID0 + 1);
        StoreAssertions.assertCartEquals(regularStore.getCart(cartID0 + 1), new ShoppingCart());
    }

    /**
     * Tests removeCart with a cart ID that is not in use.
     */
    @Test
    public void testRemoveCart0() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(6));
    }

    /**
     * Tests removeCart with 1 cart in the store.
     */
    @Test
    public void testRemoveCart1() {
        // Removes the only cart from the store, then asserts that the cart no longer exists.
        regularStore.removeCart(cartID0);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0));
    }

    /**
     * Tests removeCart on the cart with the lower ID with 2 carts in the store.
     */
    @Test
    public void testRemoveCart2() {
        // Adds a new cart to the store, removes the first one, then asserts that the first cart no longer exists and
        // that the second one still does.
        int cartID1 = regularStore.createCart();
        regularStore.removeCart(cartID0);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0));
        StoreAssertions.assertCartEquals(regularStore.getCart(cartID1), new ShoppingCart());
    }

    /**
     * Tests removeCart on the cart with the higher ID with 2 carts in the store.
     */
    @Test
    public void testRemoveCart3() {
        // Adds a new cart to the store, removes it, then asserts that the cart has been removed and that the first cart
        // is unchanged.
        int cartID1 = regularStore.createCart();
        regularStore.removeCart(cartID1);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID1));
        StoreAssertions.assertCartEquals(regularStore.getCart(cartID0), new ShoppingCart());
    }

    /**
     * Tests addToCart with all valid inputs.
     */
    @Test
    public void testAddToCart0() {
        regularStore.addToCart(cartID0, 3567, 5);
        Assertions.assertEquals(regularStore.getProductStock(3567), 4562);
        Assertions.assertEquals(regularStore.getCart(cartID0).getQuantity(3567), 5);
    }

    /**
     * Tests addToCart with a a cart ID that is not in use.
     */
    @Test
    public void testAddToCart1() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.addToCart(cartID0 + 1, 3567, 5));
    }

    /**
     * Tests addToCart with a product ID that is not in use.
     */
    @Test
    public void testAddToCart2() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.addToCart(cartID0, 8, 5));
    }

    /**
     * Tests addToCart with a negative quantity.
     */
    @Test
    public void testAddToCart3() {
        Assertions.assertThrows(InvalidParameterException.class, () -> regularStore.addToCart(cartID0, 3567, -5));
    }

    /**
     * Tests addToCart with a quantity that would result in a negative amount of stock in the inventory.
     */
    @Test
    public void testAddToCart4() {
        Assertions.assertThrows(NegativeStockException.class, () -> regularStore.addToCart(cartID0, 3567, 4568));
    }

    /**
     * Tests removeFromCart with all valid inputs.
     */
    @Test
    public void testRemoveFromCart0() {
        // Adds a product to the cart, removes it, then asserts that the stock in the inventory has returned to normal
        // and that there are 0 of that item in the cart.
        regularStore.addToCart(cartID0, 3567, 5);
        regularStore.removeFromCart(cartID0, 3567);
        Assertions.assertEquals(regularStore.getProductStock(3567), 4567);
        Assertions.assertEquals(regularStore.getCart(cartID0).getQuantity(3567), 0);
    }

    /**
     * Tests removeFromCart with a a cart ID that is not in use.
     */
    @Test
    public void testRemoveFromCart1() {
        regularStore.addToCart(cartID0, 3567, 5);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.removeFromCart(cartID0 + 1, 3567));
    }

    /**
     * Tests removeFromCart with a product ID that is not in use.
     */
    @Test
    public void testRemoveFromCart2() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.removeFromCart(cartID0, 3568));
    }

    /**
     * Tests removeFromCart with a product ID that is not in the cart (but does exist in the inventory).
     */
    @Test
    public void testRemoveFromCart3() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.removeFromCart(cartID0, 3567));
    }

    /**
     * Tests setQuantityInCart with all valid inputs.
     */
    @Test
    public void testSetQuantityInCart0() {
        regularStore.addToCart(cartID0, 3567, 5);
        regularStore.setQuantityInCart(cartID0, 3567, 10);
        Assertions.assertEquals(regularStore.getProductStock(3567), 4557);
        Assertions.assertEquals(regularStore.getCart(cartID0).getQuantity(3567), 10);
    }

    /**
     * Tests setQuantityInCart with a cart ID that is not in use.
     */
    @Test
    public void testSetQuantityInCart1() {
        regularStore.addToCart(cartID0, 3567, 5);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.setQuantityInCart(cartID0 + 1, 3567, 10));
    }

    /**
     * Tests setQuantityInCart with a product ID that is not in use.
     */
    @Test
    public void testSetQuantityInCart2() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.setQuantityInCart(cartID0, 3568, 10));
    }

    /**
     * Tests setQuantityInCart with a negative new quantity.
     */
    @Test
    public void testSetQuantityInCart3() {
        regularStore.addToCart(cartID0, 3567, 5);
        Assertions.assertThrows(InvalidParameterException.class, () -> regularStore.setQuantityInCart(cartID0, 3567, -10));
    }

    /**
     * Tests setQuantityInCart with a new quantity that would result in a negative stock in the inventory.
     */
    @Test
    public void testSetQuantityInCart4() {
        regularStore.addToCart(cartID0, 3567, 5);
        Assertions.assertThrows(NegativeStockException.class, () -> regularStore.setQuantityInCart(cartID0, 3567, 4568));
    }

    /**
     * Tests emptyCart with all valid inputs.
     */
    @Test
    public void testEmptyCart0() {
        regularStore.addToCart(cartID0, 3567, 5);
        regularStore.emptyCart(cartID0);
        Assertions.assertEquals(regularStore.getProductStock(3567), 4567);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0));
    }

    /**
     * Tests emptyCart with a cartID that is not in use.
     */
    @Test
    public void testEmptyCart1() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.emptyCart(cartID0 + 1));
    }

    /**
     * Tests processTransaction with all valid inputs.
     */
    @Test
    public void testProcessTransaction0() {
        regularStore.addToCart(cartID0, 3567, 5);
        regularStore.addToCart(cartID0, 5691, 1);
        Assertions.assertEquals(regularStore.processTransaction(cartID0), 3899.14, 0.01);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0));
    }

    /**
     * Tests processTransaction with an empty cart.
     */
    @Test
    public void testProcessTransaction1() {
        Assertions.assertEquals(regularStore.processTransaction(cartID0), 0, 0.01);
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.getCart(cartID0));
    }

    /**
     * Tests processTransaction with a cart ID that is not in use.
     */
    @Test
    public void testProcessTransaction2() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularStore.processTransaction(cartID0 + 1));
    }
}
