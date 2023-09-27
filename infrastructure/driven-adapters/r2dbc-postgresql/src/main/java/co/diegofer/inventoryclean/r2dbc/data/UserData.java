package co.diegofer.inventoryclean.r2dbc.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("User")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserData {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String branchId;

}
