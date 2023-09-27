package co.diegofer.inventoryclean.model.branch;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {

    private String id;
    private String name;
    private String location;

}
