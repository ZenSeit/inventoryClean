package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;
import co.diegofer.inventoryclean.model.values.common.RoleEnum;

public class Role implements ValueObject<String> {

    private final String role;

    public Role(String value){
        if(!RoleEnum.contains(value)) throw new IllegalArgumentException("Role is not valid");
        this.role = value;
    }

    @Override
    public String value() {
        return role;
    }

}
