//Robert Simionescu
//101143542

package mystore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * GUI manager for the store.
 *
 * @author Robert Simionescu
 * @studentNumber 101143542
 */
public class StoreView {
    private final StoreManager store;
    private final int cartID;
    private final JFrame frame;

    /**
     * Scales an image so that its largest dimension is 300px while maintaining its aspect ratio, then returns the
     * scaled image. If there is no image located at the given filepath, prints the stack trace and throws an exception.
     * @param filePath The filepath of the image to be scaled.
     * @return The scaled image.
     */
    private Image scaleImage(String filePath) {
        // Reads an image and asserts that it is not null.
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource(filePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;

        // Calculates the aspect ratio of the image
        int height = image.getHeight();
        int width = image.getWidth();
        double ratio = (float)(height/width);

        int newHeight;
        int newWidth;

        // Sets the longer side to 300px and calculates the size of the shorter side based on the ratio.
        if (height > width) {
            newHeight = 300;
            newWidth = (int) (300 * ratio);
        }
        else {
            newWidth = 300;
            newHeight = (int) (300 * ratio);
        }

        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    /**
     * Constructor for a StoreView. Takes a StoreManager and creates a new cart for use within that StoreManager. Then,
     * initializes the GUI for the StoreView.
     * @param store The Store.StoreManager for the store the user is trying to use.
     */
    StoreView(StoreManager store) {
        // Creates a cart and stores its cartID.
        this.store = store;
        cartID = store.createCart();



        // Creates the frame
        this.frame = new JFrame();
        frame.setTitle("E Store");
        frame.setPreferredSize(new Dimension(1100, 1000));



        // Initializes the labels used throughout the store.
        JLabel storeNameLabel = new JLabel("The E Store");
        storeNameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        JLabel cartNameLabel = new JLabel("Cart");
        cartNameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 25));

        // Labels for each product in the inventory. Images are added manually below. Index 0 is the product name index
        // 1 is the price, index 2 is the stock, and index 3 is the icon.
        JLabel[][] inventoryLabelArray = new JLabel[store.getInventory().size()][4];
        for (int i = 0; i < inventoryLabelArray.length; i++) {
            int productID = store.getInventory().get(i);
            inventoryLabelArray[i][0] = new JLabel(store.getProductName(productID));
            inventoryLabelArray[i][0].setFont(new Font("Sans Serif", Font.PLAIN, 20));
            inventoryLabelArray[i][1] = new JLabel(String.format("$%.02f", store.getProductPrice(productID)));
            inventoryLabelArray[i][1].setFont(new Font("Sans Serif", Font.PLAIN, 15));
            inventoryLabelArray[i][2] = new JLabel("Stock: " + store.getProductStock(productID));
            inventoryLabelArray[i][2].setFont(new Font("Sans Serif", Font.PLAIN, 15));
        }

        // Add images to the inventory labels
        inventoryLabelArray[0][3] = new JLabel(new ImageIcon(scaleImage("product images/Lowercase sans serif e.png")));
        inventoryLabelArray[1][3] = new JLabel(new ImageIcon(scaleImage("product images/Uppercase sans serif E.png")));
        inventoryLabelArray[2][3] = new JLabel(new ImageIcon(scaleImage("product images/Lowercase serif e.png")));
        inventoryLabelArray[3][3] = new JLabel(new ImageIcon(scaleImage("product images/Uppercase serif E.png")));
        inventoryLabelArray[4][3] = new JLabel(new ImageIcon(scaleImage("product images/Sans serif Ee set.png")));
        inventoryLabelArray[5][3] = new JLabel(new ImageIcon(scaleImage("product images/Serif Ee set.png")));
        inventoryLabelArray[6][3] = new JLabel(new ImageIcon(scaleImage("product images/Lowercase comic sans e.png")));
        inventoryLabelArray[7][3] = new JLabel(new ImageIcon(scaleImage("product images/Uppercase comic sans E.png")));
        inventoryLabelArray[8][3] = new JLabel(new ImageIcon(scaleImage("product images/Comic sans Ee set.png")));
        inventoryLabelArray[9][3] = new JLabel(new ImageIcon(scaleImage("product images/Defective uppercase sans serif E.png")));

        // Hashmap of labels for products in the cart. The ProductID is used as the key.
        HashMap<Integer, JLabel> cart = new HashMap<Integer, JLabel>();



        // Initialize panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel mainInventoryPanel = new JPanel(new GridLayout(0, 2, 15, 25));
        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel sidePanel = new JPanel(new BorderLayout());
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.PAGE_AXIS));
        JPanel storeHeaderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel sideHeaderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Array containing a panel for each product in the inventory
        JPanel[] inventoryPanelArray = new JPanel[inventoryLabelArray.length];
        for (int i = 0; i < inventoryPanelArray.length; i++) {
            inventoryPanelArray[i] = new JPanel(new BorderLayout());
        }



        // Creates a JScrollPane that will contain the inventory and another that will contain the cart.
        JScrollPane inventoryScrollPane = new JScrollPane(mainInventoryPanel);
        inventoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inventoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollPane cartScrollPane = new JScrollPane(cartPanel);
        cartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);



        // Adds all the product panels to the inventory panel.
        for (JPanel panel : inventoryPanelArray) {
            // The inventory panels are placed inside flowLayouts to prevent the buttons from stretching when the store is stretched.
            JPanel formattingPanel = new JPanel(new FlowLayout());
            formattingPanel.add(panel);
            panel.setMaximumSize(new Dimension(300, 400));
            mainInventoryPanel.add(formattingPanel);
        }



        // Creates the checkout button beneath the cart. Pressing the button shows the user their total and closes the store.
        // The button also displays the total cost of items in the cart at any given moment.
        JButton checkout = new JButton("Checkout: $0");
        checkout.setFont(new Font("Sans Serif", Font.PLAIN, 20));

        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, String.format("Thank you for shopping at the E Store! Your total is: $%.02f", store.getCartTotal(cartID)));
                store.processTransaction(cartID);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        checkout.setPreferredSize(new Dimension(400, 40));

        // Adds the checkout button to the side panel.
        sidePanel.add(checkout, BorderLayout.PAGE_END);

        // Adds the word "Cart" to the top of the cart.
        sidePanel.add(sideHeaderPanel, BorderLayout.PAGE_START);
        sidePanel.add(cartScrollPane, BorderLayout.CENTER);

        // Adds the inventory to the left of the body and the cart/checkout on the right.
        bodyPanel.add(inventoryScrollPane, BorderLayout.CENTER);
        bodyPanel.add(sidePanel, BorderLayout.PAGE_END);

        // Add all panels to the main panel
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.LINE_END);
        mainPanel.add(storeHeaderPanel, BorderLayout.PAGE_START);



        // Create buttons to add and remove products from the cart. The buttons are not defined in separate functions
        // as their actionPerformed methods require many variables from this scope.
        for (int i = 0; i < inventoryPanelArray.length; i++) {
            JPanel addToCartButtons = new JPanel(new GridLayout());
            int productID = store.getInventory().get(i);
            JLabel itemStock = inventoryLabelArray[i][2];

            // Adds the add to cart button to each product JPanel.
            JButton addButton = new JButton("+");
            addButton.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    if (store.getProductStock(productID) != 0) {
                        if (store.getQuantityInCart(cartID, productID) == 0) {
                            store.addToCart(cartID, productID, 1);

                            JLabel product = new JLabel(store.getProductName(productID) + "     |     " +
                                    store.getQuantityInCart(cartID, productID) + " in cart" + "     |     " +
                                    String.format("$%.02f", store.getProductPrice(productID) * store.getQuantityInCart(cartID, productID)));
                            product.setFont(new Font("Sans Serif", Font.PLAIN, 15));
                            cart.put(productID, product);

                            cartPanel.add(product);
                        }
                        else {
                            store.setQuantityInCart(cartID, productID, store.getQuantityInCart(cartID, productID) + 1);
                            cart.get(productID).setText(store.getProductName(productID) + "     |     " +
                                    store.getQuantityInCart(cartID, productID) + " in cart" + "     |     " +
                                    String.format("$%.02f", store.getProductPrice(productID) * store.getQuantityInCart(cartID, productID)));
                        }
                        itemStock.setText("Stock: " + store.getProductStock(productID));
                        checkout.setText(String.format("Checkout: $%.02f", store.getCartTotal(cartID)));

                    }
                }
            });



            JButton removeButton = new JButton("-");
            removeButton.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    if (store.getQuantityInCart(cartID, productID) != 0) {
                        if (store.getQuantityInCart(cartID, productID) == 1) {
                            store.removeFromCart(cartID, productID);
                            cartPanel.remove(cart.get(productID));
                            cartPanel.revalidate();
                            cartPanel.repaint();
                        }
                        else {
                            store.setQuantityInCart(cartID, productID, store.getQuantityInCart(cartID, productID) - 1);
                            cart.get(productID).setText(store.getProductName(productID) + "     |     " +
                                    store.getQuantityInCart(cartID, productID) + " in cart" + "     |     " +
                                    String.format("$%.02f", store.getProductPrice(productID) * store.getQuantityInCart(cartID, productID)));
                        }

                        itemStock.setText("Stock: " + store.getProductStock(productID));
                        checkout.setText(String.format("Checkout: $%.02f", store.getCartTotal(cartID)));
                    }
                }
            });

            addToCartButtons.add(addButton);
            addToCartButtons.add(removeButton);
            inventoryPanelArray[i].add(addToCartButtons, BorderLayout.PAGE_END);
        }



        // Add labels to respective panels
        storeHeaderPanel.add(storeNameLabel);
        sideHeaderPanel.add(cartNameLabel);

        // Add labels to each product in the inventory.
        for (int i = 0; i < inventoryPanelArray.length; i++) {
            JPanel productInfoPanel = new JPanel(new GridLayout(0, 1));
            productInfoPanel.add(inventoryLabelArray[i][0]);
            productInfoPanel.add(inventoryLabelArray[i][1]);
            productInfoPanel.add(inventoryLabelArray[i][2]);
            inventoryPanelArray[i].add(productInfoPanel, BorderLayout.PAGE_START);
            inventoryPanelArray[i].add(inventoryLabelArray[i][3], BorderLayout.CENTER);
        }



        // Prompt the user for confirmation if they attempt to close the store without checking out.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit without checking out?") == JOptionPane.OK_OPTION) {
                    quit();
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });



        // Adds the main panel to the frame and packs the frame.
        frame.add(mainPanel);
        frame.pack();

    }

    /**
     * Makes the GUI visible.
     */
    public void viewStore() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        Inventory i1 = new Inventory();

        i1.addProduct("Lowercase sans serif e", 0, 0.99f, 5000);
        i1.addProduct("Uppercase sans serif E", 1, 1.99f, 4500);
        i1.addProduct("Lowercase serif e", 2, 4.99f, 15);
        i1.addProduct("Uppercase serif E", 3, 9.99f, 10);
        i1.addProduct("Sans serif E/e set", 4, 2.49f, 3000);
        i1.addProduct("Serif E/e set", 5, 12.99f, 12);
        i1.addProduct("Lowercase comic sans e", 6, 24.99f, 479);
        i1.addProduct("Uppercase comic sans E", 7, 44.99f, 499);
        i1.addProduct("Comic sans E/e set", 8, 59.99f, 0);
        i1.addProduct("Defective uppercase sans serif E", 9, 0.49f, 2);

        StoreManager sm0 = new StoreManager(i1);
        StoreView store0 = new StoreView(sm0);

        store0.viewStore();
    }

    /**
     * Returns all items from the cart to the inventory and removes the cart from the list of carts in use.
     */
    private void quit() {
        store.emptyCart(cartID);
    }

}
