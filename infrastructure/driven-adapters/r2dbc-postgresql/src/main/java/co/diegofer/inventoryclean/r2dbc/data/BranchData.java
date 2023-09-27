package co.diegofer.inventoryclean.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("Branch")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchData {

    @Id
    private String id;
    private String name;
    private String location;

}
