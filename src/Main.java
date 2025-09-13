import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Global counters for generating unique IDs
    static int productId = 2; // Starting at 2 because pre-populated products use IDs 1 and 2
    static int billId = 1;    // Counter for shopping cart/bill entries

    public static void main(String[] args) {
        // Data storage: Maps to store products and shopping cart items
        Map<Integer, MakeProducts> productsMap = new HashMap<>();
        Map<Integer, ShoppingCart> billing = new HashMap<>();

        // Initialize the main window and all UI components
        JFrame container = createWindow();
        AddProduct(container, productsMap);
        ShowProducts(container, productsMap);
        showBills(container, billing);
        buyProducts(container, productsMap, billing);
        findProduct(container, productsMap);
        minMax(container, productsMap);
        Exit(container);
        container.setVisible(true);

        // Pre-populate the store with some initial products
        productsMap.put(1, new MakeProducts("milk", 22.5, 7));
        productsMap.put(2, new MakeProducts("water", 25.5, 7));
    }

    /**
     * Creates and configures the main application window
     */
    public static JFrame createWindow(){
        JFrame container = new JFrame("Little Shop");
        container.setSize(500, 500);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null); // Center the window
        container.setLayout(null); // Use absolute positioning

        // Welcome message at the top
        JLabel message = new JLabel("Greetings, What you going to do today?");
        message.setBounds(130, 20, 500, 30);
        container.add(message);
        return container;
    }

    /**
     * Creates the "Add Products" button and handles product creation
     * Validates for duplicate names, valid prices, and stock quantities
     */
    public static void AddProduct(JFrame frame, Map<Integer, MakeProducts> productMap){
        JButton add = new JButton("Add Products");
        add.setBounds(160, 75, 150, 30);
        frame.add(add);

        add.addActionListener(e -> {
            // Validation flags
            boolean priceCheck = false;
            boolean stockCheck = false;
            boolean duplicateCheck = false;
            String productName = null;

            // Product name validation loop - check for duplicates
            while (!duplicateCheck){
                boolean duplicate = false;
                try {
                    String productInputName = JOptionPane.showInputDialog(frame,"Write the name of the product.");

                    // Handle cancel button
                    if (productInputName == null) return;

                    String checkProduct = productInputName.toLowerCase().trim();

                    // Check for empty input
                    if (checkProduct.isEmpty()){
                        JOptionPane.showMessageDialog(frame, "The product name can't be empty!");
                        return;
                    }

                    // Check if product already exists
                    for (Map.Entry<Integer, MakeProducts> entry : productMap.entrySet()){
                        MakeProducts products = entry.getValue();
                        String check = products.getProductName();
                        if (check.equalsIgnoreCase(checkProduct)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate){
                        JOptionPane.showMessageDialog(frame, "The product already exists!");
                    } else {
                        productName = checkProduct;
                        duplicateCheck = true;
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error! Try again.");
                }
            }

            // Price validation loop
            double productPrice = 0;
            while (!priceCheck){
                try {
                    String priceInput = JOptionPane.showInputDialog(frame, "Write the price of the product");
                    if (priceInput == null || priceInput.isEmpty()) return;
                    productPrice = Double.parseDouble(priceInput);
                    priceCheck = true;
                } catch (NumberFormatException error){
                    JOptionPane.showMessageDialog(frame,"That's not a valid price!");
                }
            }

            // Stock validation loop
            int productStock = 0;
            while (!stockCheck){
                try {
                    String stockInput = JOptionPane.showInputDialog(frame, "Write the stock of the product;");
                    if (stockInput == null || stockInput.isEmpty()) return;
                    productStock = Integer.parseInt(stockInput);
                    stockCheck = true;
                } catch (NumberFormatException error){
                    JOptionPane.showMessageDialog(frame, "That's not a valid stock!");
                }
            }

            // Create new product and add to inventory
            MakeProducts product = new MakeProducts(productName, productPrice, productStock);
            productMap.put(productId, product);
            JOptionPane.showMessageDialog(frame, "Product added successfully!");
            productId++; // Increment for next product
        });
    }

    /**
     * Creates "Show Products" button and displays all products in inventory
     */
    public static void ShowProducts(JFrame frame, Map<Integer, MakeProducts> productsMap){
        JButton show = new JButton("Show Products");
        show.setBounds(160, 125, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Products:\n");

            // Check if inventory is empty
            if (productsMap.isEmpty()){
                JOptionPane.showMessageDialog(frame, "There's no products to show!");
                return;
            }

            // Build product list string
            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()){
                int id = entry.getKey();
                MakeProducts products = entry.getValue();
                message.append("ID: ").append(id)
                        .append(" - Name: ").append(products.getProductName())
                        .append(" - Price: ").append(products.getPrice())
                        .append(" - Stock: ").append(products.getStock())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Creates "Buy Products" button and handles the shopping/purchasing process
     * Uses multiple modal dialogs for product selection and quantity input
     */
    public static void buyProducts(JFrame frame, Map<Integer, MakeProducts> productsMap, Map<Integer, ShoppingCart> billing){
        JButton buy = new JButton("Buy Products");
        buy.setBounds(160, 175, 150, 30);
        frame.add(buy);

        buy.addActionListener(e -> {
            boolean shopping = false;

            // Main shopping loop - allows multiple purchases
            while (!shopping){
                // Display products in a non-modal dialog
                StringBuilder message = new StringBuilder("Products:\n");
                if (productsMap.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "There's no products!");
                    return;
                }

                // Build product display string
                for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
                    int id = entry.getKey();
                    MakeProducts products = entry.getValue();
                    message.append("ID: ").append(id)
                            .append(" - Name: ").append(products.getProductName())
                            .append(" - Price: ").append(products.getPrice())
                            .append(" - Stock: ").append(products.getStock())
                            .append("\n");
                }

                // Create non-modal product list dialog
                JOptionPane showPane = new JOptionPane(message.toString(), JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = showPane.createDialog(null, "Products");
                dialog.setLocation(550, 450);
                dialog.setModal(false); // Non-modal so user can see while shopping
                dialog.setVisible(true);

                // Product selection section
                String pName = null;
                JTextField textField = new JTextField(10);
                JOptionPane buyPane = new JOptionPane(
                        new Object[]{"Write the name of the product", textField},
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.OK_CANCEL_OPTION
                );
                JDialog dialogBuy = buyPane.createDialog(null, "Select a product");
                dialogBuy.setLocation(1150, 450);

                // Product name validation loop
                boolean verifyName = false;
                int productId = -1;
                while (!verifyName) {
                    dialogBuy.setModal(true);
                    dialogBuy.setVisible(true);

                    Object selectedValue = buyPane.getValue();
                    if (selectedValue != null && (int) selectedValue == JOptionPane.OK_OPTION) {
                        String input = textField.getText().trim().toLowerCase();
                        boolean found = false;
                        int stock = 0;

                        if (!input.isEmpty()) {
                            pName = input;
                            // Search for product by name
                            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
                                MakeProducts p = entry.getValue();
                                stock = p.getStock();
                                if (p.getProductName().equalsIgnoreCase(pName)) {
                                    found = true;
                                    productId = entry.getKey();
                                    break;
                                }
                            }

                            if (found){
                                if (stock == 0){
                                    JOptionPane.showMessageDialog(null, "There's not stock...");
                                } else {
                                    verifyName = true;
                                    pName = input;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "The product doesn't exists!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You need to complete the field!");
                        }
                    } else {
                        // User canceled
                        JOptionPane.showMessageDialog(null, "Canceled!");
                        dialog.setVisible(false);
                        return;
                    }
                }

                // Quantity selection section
                int quantity = 0;
                MakeProducts product = productsMap.get(productId);
                JTextField priceField = new JTextField(10);
                JOptionPane quantityPane = new JOptionPane(
                        new Object[]{"How many?", priceField},
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.OK_CANCEL_OPTION
                );
                JDialog dialogQuantity = quantityPane.createDialog(null, "Quantity");
                dialogQuantity.setLocation(1150, 450);

                boolean hasStock = false;

                // Quantity validation loop
                while (!hasStock){
                    dialogQuantity.setModal(true);
                    dialogQuantity.setVisible(true);

                    Object selectedValue = quantityPane.getValue();
                    if (selectedValue != null && (int) selectedValue == JOptionPane.OK_OPTION) {
                        String input = priceField.getText().trim().toLowerCase();
                        if (!input.isEmpty()) {
                            try {
                                int content = Integer.parseInt(input);
                                int stock = product.getStock();

                                if (content <= 0){
                                    JOptionPane.showMessageDialog(null,"You need to put a real content!");
                                } else if (content <= stock) {
                                    // Reduce stock and set quantity
                                    product.getOffStock(content);
                                    quantity = content;
                                    hasStock = true;
                                } else {
                                    JOptionPane.showMessageDialog(null,"You need to put a real content!");
                                }
                            } catch (NumberFormatException ex){
                                JOptionPane.showMessageDialog(frame, "That's a no valid stock!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You need to complete the field!");
                        }
                    } else {
                        // User canceled
                        JOptionPane.showMessageDialog(null, "Canceled!");
                        dialog.setVisible(false);
                        return;
                    }
                }

                // Calculate total and create shopping cart entry
                MakeProducts products = productsMap.get(productId);
                double price = products.getPrice();
                double total = price * quantity;

                ShoppingCart bill = new ShoppingCart(pName, quantity, price, total);
                billing.put(Integer.valueOf(billId), bill);
                billId++;

                // Debug output
                System.out.println("Product:" + pName + " quantity: " + quantity + " price: " + price + " total: " + total);

                // Ask if user wants to continue shopping
                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to buy another product?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (answer == JOptionPane.NO_OPTION) {
                    dialog.setVisible(false);
                    shopping = true; // Exit shopping loop
                } else if (answer == JOptionPane.YES_OPTION){
                    dialog.setVisible(false); // Continue shopping
                }
            }
        });
    }

    /**
     * Creates "Show Bills" button and displays purchase history
     */
    public static void showBills(JFrame frame, Map<Integer, ShoppingCart> billing){
        JButton show = new JButton("Show Bills");
        show.setBounds(160, 225, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Purchases:\n");

            // Check if there are any purchases
            if (billing.isEmpty()){
                JOptionPane.showMessageDialog(frame, "There's no bills to show!");
                return;
            }

            // Build purchase history string
            for (Map.Entry<Integer, ShoppingCart> entry : billing.entrySet()){
                int id = entry.getKey();
                ShoppingCart bills = entry.getValue();
                message.append("ID: ").append(id)
                        .append(" - Product: ").append(bills.getBuyName())
                        .append(" - Quantity: ").append(bills.getQuantity())
                        .append(" - Unit price: ").append(bills.getUnit())
                        .append(" - Total: ").append(bills.getAmount())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString(), "Purchases", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Creates "Show Prices" button and displays highest/lowest priced products
     */
    public static void minMax(JFrame frame, Map<Integer, MakeProducts> productsMap){
        JButton show = new JButton("Show Prices");
        show.setBounds(160, 275, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            if (productsMap.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No products to show!");
                return;
            }

            // Initialize tracking variables for max price product
            int maxProductId = 0;
            String maxName = "";
            double maxValue = Double.MIN_VALUE;

            // Initialize tracking variables for min price product
            int minProductId = 0;
            String minName = "";
            double minValue = Double.MAX_VALUE;

            // Find products with highest and lowest prices
            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
                MakeProducts product = entry.getValue();
                int idProduct = entry.getKey();
                double price = product.getPrice();
                String name = product.getProductName();

                // Check for highest price
                if (price > maxValue) {
                    maxValue = price;
                    maxProductId = idProduct;
                    maxName = name;
                }

                // Check for lowest price
                if (price < minValue) {
                    minValue = price;
                    minProductId = idProduct;
                    minName = name;
                }
            }

            // Display results
            String message = "Highest price product:\n" +
                    "ID: " + maxProductId + " - Name: " + maxName + " - Price: $" + maxValue + "\n\n" +
                    "Lowest price product:\n" +
                    "ID: " + minProductId + " - Name: " + minName + " - Price: $" + minValue;

            JOptionPane.showMessageDialog(frame, message, "Price Extremes", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Creates "Find Product" button and handles product search by name
     */
    public static void findProduct(JFrame frame, Map<Integer, MakeProducts> productsMap) {
        JButton find = new JButton("Find Product");
        find.setBounds(160, 325, 150, 30);
        frame.add(find);

        find.addActionListener(e -> {
            if (productsMap.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No products available!");
                return;
            }

            // Create search dialog
            JTextField textField = new JTextField(10);
            JOptionPane findPane = new JOptionPane(
                    new Object[]{"Write the name of the product", textField},
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION
            );
            JDialog dialogFind = findPane.createDialog(frame, "Find Product");
            dialogFind.setLocation(825, 450);

            boolean foundProduct = false;

            // Search loop
            while (!foundProduct) {
                dialogFind.setModal(true);
                dialogFind.setVisible(true);

                Object selectedValue = findPane.getValue();
                if (selectedValue != null && (int) selectedValue == JOptionPane.OK_OPTION) {
                    String input = textField.getText().trim().toLowerCase();
                    if (!input.isEmpty()) {
                        // Search through all products
                        for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
                            MakeProducts p = entry.getValue();
                            if (p.getProductName().equalsIgnoreCase(input)) {
                                // Product found - display info
                                String message = "Product Found:\n" +
                                        "ID: " + entry.getKey() +
                                        " - Name: " + p.getProductName() +
                                        " - Price: " + p.getPrice() +
                                        " - Stock: " + p.getStock();
                                JOptionPane.showMessageDialog(frame, message, "Product Info", JOptionPane.INFORMATION_MESSAGE);
                                foundProduct = true;
                                break;
                            }
                        }
                        if (!foundProduct) {
                            JOptionPane.showMessageDialog(frame, "Product not found!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "You need to enter a product name!");
                    }
                } else {
                    // User canceled search
                    foundProduct = true;
                }
            }
        });
    }

    /**
     * Creates "Exit" button and handles application termination
     */
    public static void Exit(JFrame frame){
        JButton out = new JButton("Exit");
        out.setBounds(160, 375, 150, 30);
        frame.add(out);

        String[] buttons = {"Yes", "No"};
        out.addActionListener(e -> {
            // Confirm exit with user
            int confirm = JOptionPane.showOptionDialog(
                    frame,
                    "Do you want to exit?",
                    "Confirm",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    buttons,
                    buttons[0]
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Terminate application
            }
        });
    }
}