package co.diegofer.inventoryclean.model.product;
import lombok.*;

import java.io.Serializable;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product implements Serializable {

    private String id;
    private String name;
    private String description;
    private int inventoryStock;
    private double price;
    private String category;
    private String branchId;

    public Product(String name, String description, int inventoryStock, double price, String category, String branchId) {
        this.name = name;
        this.description = description;
        this.inventoryStock = inventoryStock;
        this.price = price;
        this.category = category;
        this.branchId = branchId;
    }
}
