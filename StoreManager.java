//Robert Simionescu
//101143542

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class StoreManager {
    private Inventory inventory;

    public StoreManager() {
        inventory = new Inventory();
    }

    /*
    Returns the stock of an item in the inventory with a given ID.
     */
    public int getStock(int id) {
        return(inventory.getStock(id));
    }


    /*
    Takes an ArrayList of integer arrays containing a product ID at index 0 and a quantity to purchase at index 1.
    Attempts to remove the given quantity from the stock of the given products. If any products fail, returns -1 and no
    change is made to the stock of any products. If successful, returns the total price of the purchase.
     */
    public float makeTransaction(ArrayList<int[]> cart) {
        float totalCost = 0;

        //Removes stock from all items one at a time and adds to the total cost.
        for (int i = 0; i < cart.size(); i++) {
            try {
                inventory.removeStock(cart.get(i)[0], cart.get(i)[1]);
                totalCost += inventory.getPrice(cart.get(i)[0]) * cart.get(i)[1];
            }
            catch(InvalidParameterException e) {    //If any of the products do not have enough stock, returns stock to all that have had it removed and returns -1.
                while (i > 0) {
                    i--;
                    inventory.addStock(cart.get(i)[0], cart.get(i)[1]);
                }
                return (-1);
            }
        }
        return totalCost;
    }
}
