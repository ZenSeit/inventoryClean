package co.diegofer.inventoryclean.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductData {

    @Id
    private String id;
    private String name;
    private String description;
    private int inventoryStock;
    private float price;
    private String category;
    private String branchId;
}
