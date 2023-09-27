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
    private String category;
    private String branchId;

}
