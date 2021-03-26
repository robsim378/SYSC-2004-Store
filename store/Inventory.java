//Robert Simionescu
//101143542

package store;

import storeexceptions.EmptyNameException;
import storeexceptions.InvalidIDException;
import storeexceptions.NegativePriceException;
import storeexceptions.NegativeStockException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Class representing a store inventory.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class Inventory {
    /**
     * An ArrayList containing all the products sorted in increasing order of IDs.
     */
    private final ArrayList<ProductStock> products;

    /**
     * Constructor for an Store.Inventory. Creates an empty ArrayList of ProductStocks.
     */
    public Inventory() {
        this.products = new ArrayList<ProductStock>();
    }

    /**
     * Returns a list of the IDs of all the products in the inventory.
     *
     * @return an ArrayList of Integers containing all the products in the inventory.
     */
    public ArrayList<Integer> getInventory() {
        ArrayList<Integer> inventory = new ArrayList<Integer>();
        for (ProductStock product : products) {
            inventory.add(product.getProduct().getId());
        }
        return inventory;
    }

    /**
     * Adds a productStock containing the specified product name, ID, price, and initial stock to products. If the name is
     * an empty string, the price or stock is negative, or the ID is already taken, throws a custom Exception.
     *
     * @param name         The name of the new product stored as a String.
     * @param id           The ID of the product stored as an int.
     * @param price        The price of the product stored as a float.
     * @param initialStock The initial stock of the product stored as an int.
     */
    public void addProduct(String name, int id, float price, int initialStock) {
        Product product = new Product(name, id, price);

        // Throws exceptions if name or price are invalid.
        if (name.equals("")) {
            throw new EmptyNameException("Name cannot be empty string");
        }
        if (price < 0) {
            throw new NegativePriceException("Price cannot be negative");
        }
        if (initialStock < 0) {
            throw new NegativeStockException("Stock cannot be negative");
        }

        // Finds the index at which to insert the new productStock
        int i = 0;
        while (i < products.size() && products.get(i).getProduct().getId() < id) {
            i++;
        }

        // Checks if the ID is already taken and raises an exception if it is.
        if (i != products.size() && products.get(i).getProduct().getId() == id) {
            throw new InvalidIDException("ID " + id + " already in use");
        }

        products.add(i, new ProductStock(product, initialStock));
    }

    /**
     * Add or remove a specified amount of stock of a specified product from the inventory. If stockIncrease is negative,
     * stock will be removed. If stock goes below zero while removing, stock will remain unchanged and an
     * NegativeStockException will be thrown.
     *
     * @param id            The ID of the product to change the stock of.
     * @param stockIncrease How much to increase the stock by. Use a negative int to decrease the stock.
     */
    private void changeStock(int id, int stockIncrease) {
        int index = findProduct(id);
        int currentStock = products.get(index).getStock();
        if (currentStock + stockIncrease < 0) {
            throw new NegativeStockException("Stock cannot go below zero");
        }
        products.get(index).setStock(currentStock + stockIncrease);
    }

    /**
     * Increase the stock for the Store.Product with the given ID by the given amount.
     *
     * @param id            The ID of the product to increase the stock of.
     * @param stockIncrease How much to increase the stock by. Must be a positive int.
     */
    public void addStock(int id, int stockIncrease) {
        //Throw an exception if the user attempts to increase the stock by a negative amount.
        if (stockIncrease < 0) {
            throw new InvalidParameterException("Stock cannot be increased by a negative amount. To remove stock, use removeStock method instead.");
        } else if (stockIncrease > 0) {
            changeStock(id, stockIncrease);
        }
    }

    /**
     * Decrease the stock for the Store.Product with the given ID by the given amount.
     *
     * @param id            The ID of the product to decrease the stock of.
     * @param stockDecrease How much to decrease the stock by. Must be a positive int.
     */
    public void removeStock(int id, int stockDecrease) {
        //Throw an exception if the user attempts to increase the stock by a negative amount.
        if (stockDecrease < 0) {
            throw new InvalidParameterException("Stock cannot be decreased by a negative amount. To add stock, use addStock method instead.");
        } else if (stockDecrease > 0) {
            changeStock(id, -stockDecrease);
        }
    }

    /**
     * Performs a binary search on the ArrayList products and returns the index of the product in the products ArrayList.
     * If the product is not found, throws an InvalidIDException.
     *
     * @param id The ID of the product to find.
     * @return The index of the product with the given ID stored as an int.
     */
    private int findProduct(int id) {
        int min = 0;
        int max = this.products.size() - 1;

        while (min != max) {
            int mid = min + (max - min) / 2;
            Product current = products.get(mid).getProduct();
            if (current.getId() == id) {
                return (mid);
            }
            if (current.getId() > id) {
                max = mid;
            } else if (current.getId() < id) {
                min = mid + 1;
            }
        }
        if (products.get(min).getProduct().getId() == id) {
            return min;
        }
        throw new InvalidIDException("No product exists with ID " + id);
    }

    /**
     * Getter for the stock of the product with the given ID.
     *
     * @param id The product to get the stock of.
     * @return The remaining stock of the product with the given ID stored as an int.
     */
    public int getStock(int id) {
        return (products.get(findProduct(id)).getStock());
    }

    /**
     * Getter for the product with the given ID.
     *
     * @param id The product to get the name of.
     * @return The name of the product stored as a String.
     */
    public String getName(int id) {
        return (products.get(findProduct(id)).getProduct().getName());
    }

    /**
     * Getter for the price of the product with the given ID.
     *
     * @param id The product to get the price of.
     * @return The price of the product stored as a float.
     */
    public float getPrice(int id) {
        return (products.get(findProduct(id)).getProduct().getPrice());
    }

    /**
     * Internal class used to associate a product with its stock in the inventory.
     */
    private class ProductStock {
        private Product product;
        private int stock;

        /**
         * Constructor for a ProductStock. Takes a product and its initial stock in the inventory.
         *
         * @param product      The product stored as a Store.Product.
         * @param initialStock The initial stock of the product stored as an int.
         */
        public ProductStock(Product product, int initialStock) {
            this.product = product;
            this.stock = initialStock;
        }

        /**
         * Getter for the product.
         *
         * @return The product stored as a Store.Product.
         */
        public Product getProduct() {
            return product;
        }

        /**
         * Setter for the product.
         *
         * @param product The Store.Product to be put into the ProductStock.
         */
        public void setProduct(Product product) {
            this.product = product;
        }

        /**
         * Getter for the stock of the product.
         *
         * @return The stock of the product in the inventory stored as an int.
         */
        public int getStock() {
            return stock;
        }

        /**
         * The new stock of the product.
         *
         * @param stock
         */
        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
