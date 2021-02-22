//Robert Simionescu
//101143542

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Inventory {
    /*
    Inner class pairing a Product and its stock together..
     */
    private class ProductStock {

        private Product product;
        private int stock;

        public ProductStock(Product product, int initialStock) {
            this.product = product;
            this.stock = initialStock;
        }

        public Product getProduct() {
            return product;
        }

        public int getStock() {
            return stock;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }


    /*
    An ArrayList containing all the products sorted in increasing order of IDs.
     */
    private ArrayList<ProductStock> products;


    public Inventory() {
        this.products = new ArrayList<ProductStock>();
    }


    /*
    Adds a productStock containing the specified product name, ID, price, and initial stock to products. If the name is
    an empty string, the price or stock is negative, or the ID is already taken, throws an InvalidParameterException.
     */
    public void addProduct(String name, int id, float price, int initialStock) {
        Product product = new Product(name, id, price);

        //Throws exceptions if name or price are invalid.
        if (name.equals("")) {
            throw new InvalidParameterException("Name cannot be empty string");
        }
        if (price < 0) {
            throw new InvalidParameterException("Price cannot be negative");
        }
        if (initialStock < 0) {
            throw new InvalidParameterException("Stock cannot be negative");
        }

        //Finds the index at which to insert the new productStock
        int i = 0;
        while (i < products.size() && products.get(i).getProduct().getId() < id) {
            i++;
        }

        //Checks if the ID is already taken and raises an exception if it is.
        if (i != products.size() && products.get(i).getProduct().getId() == id) {
            throw new InvalidParameterException("ID " + id + " already in use");
        }

        products.add(i, new ProductStock(product, initialStock));
    }


    /*
    Add or remove a specified amount of stock of a specified product from the inventory. If stockIncrease is negative,
    stock will be removed. If stock goes below zero while removing, stock will remain unchanged and an
    InvalidParameterException will be thrown.
     */
    private void changeStock(int id, int stockIncrease) {
        int index = findProduct(id);
        int currentStock = products.get(index).getStock();
        if (currentStock + stockIncrease < 0) {
            throw new InvalidParameterException("Stock cannot go below zero");
        }
        products.get(index).setStock(currentStock + stockIncrease);
    }


    /*
    Increase the stock for the Product with the given ID by the given amount.
     */
    public void addStock(int id, int stockIncrease) {
        //Throw an exception if the user attempts to increase the stock by a negative amount.
        if (stockIncrease < 0) {
            throw new InvalidParameterException("Stock cannot be increased by a negative amount. To remove stock, use removeStock method instead.");
        }
        else if (stockIncrease > 0) {
            changeStock(id, stockIncrease);
        }
    }


    /*
    Decrease the stock for the Product with the given ID by the given amount.
     */
    public void removeStock(int id, int stockDecrease) {
        //Throw an exception if the user attempts to increase the stock by a negative amount.
        if (stockDecrease < 0) {
            throw new InvalidParameterException("Stock cannot be decreased by a negative amount. To add stock, use addStock method instead.");
        }
        else if (stockDecrease > 0) {
            changeStock(id, -stockDecrease);
        }
    }


    /*
    Performs a binary search on the ArrayList products and returns the index of the product in the products ArrayList.
    If the product is not found, throws an InvalidParameterException.
     */
    private int findProduct(int id) {
        int min = 0;
        int max = this.products.size() - 1;

        while(min != max) {
            int mid = min + (max - min) / 2;
            Product current = products.get(mid).getProduct();
            if (current.getId() == id) {
                return(mid);
            }
            if (current.getId() > id) {
                max = mid;
            }
            else if (current.getId() < id) {
                min = mid + 1;
            }
        }
        if (products.get(min).getProduct().getId() == id) {
            return min;
        }
        throw new InvalidParameterException("No product exists with ID " + id);
    }


    /*
    Returns the stock of a product with a given id.
     */
    public int getStock(int id) {
        return(products.get(findProduct(id)).getStock());
    }


    /*
    Returns the name of a product with a given id.
     */
    public String getName(int id) {
        return(products.get(findProduct(id)).getProduct().getName());
    }


    /*
    Returns the price of a product with a given id.
     */
    public float getPrice(int id) {
        return(products.get(findProduct(id)).getProduct().getPrice());
    }
}
