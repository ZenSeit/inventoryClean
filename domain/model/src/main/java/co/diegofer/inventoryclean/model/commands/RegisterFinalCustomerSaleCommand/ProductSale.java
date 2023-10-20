package co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand;

public class ProductSale {
    String id;
    String name;
    int quantity;

    public ProductSale() {
    }

    public ProductSale(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }



    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }
}
