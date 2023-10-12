package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;
import co.diegofer.inventoryclean.model.values.common.RoleEnum;

public class Role implements ValueObject<String> {

    private final String role;

    public Role(String value){
        try {
            RoleEnum.valueOf(value.toUpperCase());
            this.role = value.toUpperCase();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role is not valid");
        }
    }

    @Override
    public String value() {
        return role;
    }

}
