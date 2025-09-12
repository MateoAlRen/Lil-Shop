import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public class Main {
    static int productId = 2;
    static int billId = 1;
    public static void main(String[] args) {
        Map<Integer, MakeProducts> productsMap = new HashMap<>();
        Map<Integer, ShoppingCart> billing = new HashMap<>();
        JFrame container = createWindow();
        AddProduct(container, productsMap);
        ShowProducts(container,productsMap);
        showBills(container,billing);
        buyProducts(container,productsMap, billing);
        minMax(container,productsMap);
        Exit(container);
        container.setVisible(true);

        productsMap.put(1, new MakeProducts("Milk", 22.5,7));
        productsMap.put(2, new MakeProducts("Water", 25.5,7));

    }

    public static JFrame createWindow(){
        JFrame container = new JFrame("Little Shop");
        container.setSize(500, 500);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        JLabel message = new JLabel("Greetings, What you going to do today?");
        message.setBounds(130, 20, 500, 30);
        container.add(message);
        return container;
    }

    public static void AddProduct(JFrame frame, Map<Integer, MakeProducts> productMap){
        JButton add = new JButton("Add Products");
        add.setBounds(160, 75, 150, 30);
        frame.add(add);
        // String[] buttons = {"Add Product", "Add Price", "Add Stock", "Cancel"};
        add.addActionListener(e -> {
            boolean priceCheck = false;
            boolean stockCheck = false;
            boolean duplicateCheck = false;
            String productName = null;
            while (!duplicateCheck){
                boolean duplicate = false;
                try {
                    String productInputName = JOptionPane.showInputDialog(frame,"Write the name of the product.");
                    String checkProduct = productInputName.toLowerCase();
                    if (checkProduct.isEmpty()){
                        JOptionPane.showMessageDialog(frame, "The product already it's null!");
                        return;
                    }

                    for (Map.Entry<Integer, MakeProducts> entry : productMap.entrySet()){
                        MakeProducts products = entry.getValue();
                        String check = products.getProductName();
                        if (check.equals(checkProduct)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate){
                        JOptionPane.showMessageDialog(frame, "The product already exits!");
                    } else {
                        productName = productInputName.toLowerCase();
                        duplicateCheck = true;
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "The product already exits or it's null!");
                }
            }
            double productPrice = 0;
            while (!priceCheck){
                try {
                    String priceInput = JOptionPane.showInputDialog(frame, "Write the price of the product");
                    if (priceInput == null || priceInput.isEmpty()) return;
                    productPrice = Double.parseDouble(priceInput);
                    priceCheck = true;
                } catch (NumberFormatException error){
                    JOptionPane.showMessageDialog(frame,"That's a not valid price!");
                }
            }

            int productStock = 0;
            while (!stockCheck){
                try {
                    String stockInput = JOptionPane.showInputDialog(frame, "Write the stock of the product;");
                    if (stockInput == null || stockInput.isEmpty()) return;
                    productStock = Integer.parseInt(stockInput);
                    stockCheck = true;
                } catch (NumberFormatException error){
                    JOptionPane.showMessageDialog(frame, "That's a no valid stock!");
                }
            }

            MakeProducts product = new MakeProducts(productName,productPrice,productStock);
            productMap.put(productId, product);
            JOptionPane.showMessageDialog(frame, "Product added successfully!");
            productId++;

        });
    }

    public static void ShowProducts(JFrame frame, Map<Integer, MakeProducts> productsMap){
        JButton show = new JButton("Show Products");
        show.setBounds(160, 125, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Products:\n");
            if (productsMap.isEmpty()){
                JOptionPane.showMessageDialog(frame, "There's no products to show!");
                return;
            }

            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()){
                int id = entry.getKey();
                MakeProducts products = entry.getValue();
                message.append("ID: ").append(id)
                        .append(" - Name: ").append(products.getProductName())
                        .append(" - Price: ").append(products.getPrice())
                        .append(" - Stock: ").append(products.getStock())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame,message.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
        });
    }

        public static void buyProducts(JFrame frame, Map<Integer, MakeProducts> productsMap, Map<Integer, ShoppingCart> billing){
            JButton buy = new JButton("Buy Products");
            buy.setBounds(160, 175, 150, 30);
            frame.add(buy);
            buy.addActionListener(e -> {
                boolean shopping = false;
                while (!shopping){
                    StringBuilder message = new StringBuilder("Products:\n");
                    if (productsMap.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "There's no products!");
                        return;
                    }

                    for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
                        int id = entry.getKey();
                        MakeProducts products = entry.getValue();
                        message.append("ID: ").append(id)
                                .append(" - Name: ").append(products.getProductName())
                                .append(" - Price: ").append(products.getPrice())
                                .append(" - Stock: ").append(products.getStock())
                                .append("\n");
                    }
                    JOptionPane showPane = new JOptionPane(
                            message.toString(),
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    JDialog dialog = showPane.createDialog(null, "Products");
                    dialog.setLocation(550, 450);
                    dialog.setModal(false);
                    dialog.setVisible(true);

                    // Shopping Cart

                    // Product name && ID
                    String pName = null;

                    JTextField textField = new JTextField(10);
                    JOptionPane buyPane = new JOptionPane(
                            new Object[]{"Write the name of the product", textField},
                            JOptionPane.QUESTION_MESSAGE,
                            JOptionPane.OK_CANCEL_OPTION
                    );
                    JDialog dialogBuy = buyPane.createDialog(null, "Select a product");
                    dialogBuy.setLocation(1150, 450);

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
                            JOptionPane.showMessageDialog(null, "Canceled!");
                            dialog.setVisible(false);
                            return;
                        }
                    }

                    // How many products
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
                            JOptionPane.showMessageDialog(null, "Canceled!");
                            dialog.setVisible(false);
                            return;
                        }
                    }

                    MakeProducts products = productsMap.get(productId);
                    double price = products.getPrice();
                    double total = price * quantity;

                    ShoppingCart bill = new ShoppingCart(pName,quantity,price,total);
                    billing.put(billId,bill);
                    billId++;
                    System.out.println("Product:" + pName + " quantity: " + quantity + " price: " + price + " total: " + total);
                    int answer = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to buy another product?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (answer == JOptionPane.NO_OPTION) {
                        dialog.setVisible(false);
                        shopping = true;
                    }
                }
            });
    }

    public static void showBills(JFrame frame,Map<Integer, ShoppingCart> billing){
        JButton show = new JButton("Show Bills");
        show.setBounds(160, 225, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Purchases:\n");
            if (billing.isEmpty()){
                JOptionPane.showMessageDialog(frame, "There's no bills to show!");
                return;
            }

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
            JOptionPane.showMessageDialog(frame,message.toString(), "Purchases", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void minMax (JFrame frame, Map<Integer,MakeProducts> productsMap){
        JButton show = new JButton("Show Prices");
        show.setBounds(160, 275, 150, 30);
        frame.add(show);

        show.addActionListener(e -> {
            int maxProduct = 0;
            double maxValue = Double.MIN_VALUE;
            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()){
                MakeProducts product = entry.getValue();
                int idProduct = entry.getKey();
                double price = product.getPrice();
                if (price > maxValue){
                    maxValue = price;
                    maxProduct = idProduct;
                }
            }

            int minProduct = 0;
            double minValue = Double.MAX_VALUE;
            for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()){
                MakeProducts product = entry.getValue();
                int idProduct = entry.getKey();
                double price = product.getPrice();
                if (price < minValue){
                    minValue = price;
                    minProduct = idProduct;
                }
            }

            System.out.println(minProduct + " " + maxProduct);
        });
    }

    public static void Exit(JFrame frame){
        JButton out = new JButton("Exit");
        out.setBounds(160, 325, 150, 30);
        frame.add(out);
        String[] buttons = {"Yes", "No"};
        out.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(frame, "Do you want to exit?", "Confirm", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null, buttons, buttons[0]);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
