public class MakeProducts {
    private final String productName;
    private final double price;
    private int stock;

    public MakeProducts(String productName, double price, int stock){
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public String getProductName(){
        return productName;
    }

    public double getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }


    public int getOffStock(int num){
        if (num > this.stock){
            System.out.println("There's no many products!");
        } else {
            this.stock -= num;
        }
        return this.stock;
    }
}
