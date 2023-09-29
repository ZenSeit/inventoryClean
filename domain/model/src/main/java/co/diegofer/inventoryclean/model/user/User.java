package co.diegofer.inventoryclean.model.user;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String branchId;

    public User(String name, String lastName, String email, String password, String role, String branchId) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }

}
