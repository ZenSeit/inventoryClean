package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Role implements ValueObject<String> {

    private final String role;

    public Role(String value){
        this.role = value;
    }

    @Override
    public String value() {
        return role;
    }

}
