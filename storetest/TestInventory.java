// Robert Simionescu
// 101143542

package storetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.Inventory;
import storeexceptions.InvalidIDException;
import storeexceptions.NegativePriceException;
import storeexceptions.NegativeStockException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Unit tests for Inventory.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class TestInventory {
    static Inventory emptyInventory;    // An empty Inventory to test the only edge case for the state of an Inventory.
    static Inventory regularInventory;  // A non-empty Inventory to test on an ordinary Inventory.

    /**
     * Initializes two inventories and adds a few items to test basic functionality and various edge cases.
     */
    @BeforeEach
    public void init() {
        emptyInventory = new Inventory();
        regularInventory = new Inventory();
        regularInventory.addProduct("no stock product", 5692, 799.994f, 0);
        regularInventory.addProduct("1 stock product", 5691, 899.989f, 1);
        regularInventory.addProduct("ID 0 product", 0, 999.655f, 2);
        regularInventory.addProduct("regular product", 3567, 599.832576f, 4567);
    }

    /**
     * Tests getInventory on an empty Inventory.
     */
    @Test
    public void testGetInventory0() {
        ArrayList<Integer> emptyInventoryTest = new ArrayList<Integer>();
        Assertions.assertEquals(emptyInventory.getInventory(), emptyInventoryTest);
    }

    /**
     * Tests getInventory on a non-empty Inventory.
     */
    @Test
    public void testGetInventory1() {
        ArrayList<Integer> regularInventoryTest = new ArrayList<Integer>();
        // addProduct inserts products in such a way that the inventory is sorted by product ID, so these are added in
        // ascending order.
        regularInventoryTest.add(0);
        regularInventoryTest.add(3567);
        regularInventoryTest.add(5691);
        regularInventoryTest.add(5692);

        Assertions.assertEquals(regularInventory.getInventory(), regularInventoryTest);
    }

    /**
     * Tests getStock for a product with no stock.
     */
    @Test
    public void testGetStock0() {
        Assertions.assertEquals(regularInventory.getStock(5692), 0);
    }

    /**
     * Tests getStock for a product with stock.
     */
    @Test
    public void testGetStock1() {
        Assertions.assertEquals(regularInventory.getStock(3567), 4567);
    }

    /**
     * Tests getStock for an invalid ID.
     */
    @Test
    public void testGetStock2() {
        // Asserts that attempting to get the stock of a product that does not exist throws an InvalidIDException.
        // Note that this specific case is only tested here as the exception is thrown by a private method used by all
        // subsequent public methods tested and as such is guaranteed to be thrown for an invalid ID.
        Assertions.assertThrows(InvalidIDException.class, () -> regularInventory.getStock(5));
    }

    /**
     * Tests getName on a product.
     */
    @Test
    public void testGetName0() {
        Assertions.assertEquals(regularInventory.getName(3567), "regular product");
    }

    /**
     * Tests getPrice on a product.
     */
    @Test
    public void testGetPrice0() {
        Assertions.assertEquals(regularInventory.getPrice(0), 999.655f, 0.01);
    }

    /**
     * Tests addStock with a valid amount of stock to add.
     */
    @Test
    public void testAddStock0() {
        regularInventory.addStock(3567, 5);
        Assertions.assertEquals(regularInventory.getStock(3567), 4572);
    }

    /**
     * Tests addStock with a negative amount of stock to add.
     */
    @Test
    public void testAddStock1() {
        // Asserts that attempting to remove a negative amount of stock results in an InvalidParameterException
        Assertions.assertThrows(InvalidParameterException.class, () -> regularInventory.addStock(3567, -5));
    }

    /**
     * Tests removeStock with a valid amount of stock to remove.
     */
    @Test
    public void testRemoveStock0() {
        regularInventory.removeStock(3567, 5);
        Assertions.assertEquals(regularInventory.getStock(3567), 4562);
    }

    /**
     * Tests removeStock with an amount of stock to remove that results in negative stock.
     */
    @Test
    public void testRemoveStock1() {
        // Asserts that attempting to remove more stock than there is in the inventory results in a NegativeStockException.
        Assertions.assertThrows(NegativeStockException.class, () -> regularInventory.removeStock(3567, 4568));
    }

    /**
     * Tests removeStock with a negative amount of stock to remove.
     */
    @Test
    public void testRemoveStock2() {
        // Asserts that attempting to remove a negative amount of stock results in an InvalidParameterException
        Assertions.assertThrows(InvalidParameterException.class, () -> regularInventory.removeStock(3567, -5));
    }

    /**
     * Tests addProduct for a regular product.
     */
    @Test
    public void testAddProduct0() {
        regularInventory.addProduct("testproduct", 25, 657.89f, 576);

        Assertions.assertEquals(regularInventory.getName(25), "testproduct");
        Assertions.assertEquals(regularInventory.getPrice(25), 657.89f);
        Assertions.assertEquals(regularInventory.getStock(25), 576);
    }

    /**
     * Tests addProduct for a product with an invalid name.
     */
    @Test
    public void testAddProduct1() {
        Assertions.assertThrows(InvalidParameterException.class, () -> regularInventory.removeStock(3567, -5));
    }

    /**
     * Tests addProduct for a product with an ID that is already in use.
     */
    @Test
    public void testAddProduct2() {
        Assertions.assertThrows(InvalidIDException.class, () -> regularInventory.addProduct("testproduct", 5692, 657.89f, 576));
    }

    /**
     * Tests addProduct for a product with a negative price.
     */
    @Test
    public void testAddProduct3() {
        Assertions.assertThrows(NegativePriceException.class, () -> regularInventory.addProduct("testproduct", 25, -1f, 576));
    }

    /**
     * Tests addProduct for a product with a negative initial stock.
     */
    @Test
    public void testAddProduct4() {
        Assertions.assertThrows(NegativeStockException.class, () -> regularInventory.addProduct("testproduct", 25, 657.89f, -1));
    }
}
