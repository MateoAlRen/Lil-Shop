public class ShoppingCart {
    private final String buyName;
    private final int quantity;
    private final double unit;
    private final double amount;

    public ShoppingCart(String buyName, int quantity,double unit, double amount){
        this.buyName = buyName;
        this.quantity = quantity;
        this.unit = unit;
        this.amount = amount;

    }

    public String getBuyName() {
        return buyName;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getUnit(){
        return unit;
    }

    public double getAmount(){
        return amount;
    }

}
