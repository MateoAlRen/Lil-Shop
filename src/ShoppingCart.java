// Represents a purchase/bill entry
public class ShoppingCart {
    private final String buyName;  // Product name
    private final int quantity;    // Units bought
    private final double unit;     // Price per unit
    private final double amount;   // Total cost

    // Constructor - creates a new purchase record
    public ShoppingCart(String buyName, int quantity, double unit, double amount){
        this.buyName = buyName;
        this.quantity = quantity;
        this.unit = unit;
        this.amount = amount;
    }

    // Returns product name
    public String getBuyName() {
        return buyName;
    }

    // Returns quantity purchased
    public int getQuantity(){
        return quantity;
    }

    // Returns unit price
    public double getUnit(){
        return unit;
    }

    // Returns total amount
    public double getAmount(){
        return amount;
    }
}