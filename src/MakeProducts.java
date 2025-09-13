// Represents a product in the store inventory
public class MakeProducts {
    private final String productName;  // Product name (immutable)
    private final double price;        // Product price (immutable)
    private int stock;                 // Available stock (can change)

    // Constructor - creates a new product
    public MakeProducts(String productName, double price, int stock){
        this.productName = productName;  // Set product name
        this.price = price;              // Set product price
        this.stock = stock;              // Set initial stock
    }

    // Returns product name
    public String getProductName(){
        return productName;
    }

    // Returns product price
    public double getPrice(){
        return price;
    }

    // Returns current stock
    public int getStock(){
        return stock;
    }

    // Reduces stock by specified amount
    public int getOffStock(int num){
        if (num > this.stock){
            System.out.println("There's no many products!");  // Not enough stock
        } else {
            this.stock -= num;  // Subtract from stock
        }
        return this.stock;  // Return remaining stock
    }
}

