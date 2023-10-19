package co.diegofer.inventoryclean.model.commands.custom;

public class ProductToAdd {

    String productId;
    private Integer quantity;

    public ProductToAdd(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
