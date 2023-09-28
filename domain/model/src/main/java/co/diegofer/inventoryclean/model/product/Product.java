package co.diegofer.inventoryclean.model.product;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

    private String id;
    private String name;
    private String description;
    private int inventoryStock;
    private float price;
    private String category;
    private String branchId;

    public Product(String name, String description, int inventoryStock, float price, String category, String branchId) {
        this.name = name;
        this.description = description;
        this.inventoryStock = inventoryStock;
        this.price = price;
        this.category = category;
        this.branchId = branchId;
    }
}
