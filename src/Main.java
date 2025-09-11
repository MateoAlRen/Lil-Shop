import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static int productId = 1;
    public static void main(String[] args) {
        Map<Integer, MakeProducts> productsMap = new HashMap<>();
        JFrame container = createWindow();
        AddProduct(container, productsMap);
        ShowProducts(container,productsMap);
        Exit(container);
        container.setVisible(true);

        MakeProducts milk = new MakeProducts("Milk",22.6,7);

        System.out.println(milk.getProductName() + " " + milk.getPrice() + " " + milk.getStock());
        int milkStock = milk.getStock();
        int purchases = milkStock - milk.getOffStock(5);
        System.out.println(milk.getStock());

        for (Map.Entry<Integer, MakeProducts> entry : productsMap.entrySet()) {
            Integer id = entry.getKey();
            MakeProducts product = entry.getValue();
            System.out.println("ID: " + id + " - Name: " + product.getProductName() + " - Price: " + product.getPrice());
        }
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
        add.setBounds(160, 50, 150, 30);
        frame.add(add);
        // String[] buttons = {"Add Product", "Add Price", "Add Stock", "Cancel"};
        add.addActionListener(e -> {
            boolean priceCheck = false;
            boolean stockCheck = false;
            String productName = JOptionPane.showInputDialog(frame,"Write the name of the product.");
            if (productName == null || productName.isEmpty()) return;
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
        show.setBounds(160, 100, 150, 30);
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
                        .append("- Name: ").append(products.getProductName())
                        .append("- Price: ").append(products.getPrice())
                        .append("- Stock: ").append(products.getStock())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame,message.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
        });


    }

    public static void Exit(JFrame frame){
        JButton out = new JButton("Exit");
        out.setBounds(160, 150, 150, 30);
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
