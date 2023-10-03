package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Password implements ValueObject<String> {

    private final String password;

    public Password(String value){
        if(value == null || value.length() < 8) throw new IllegalArgumentException("Password cannot be null or less than 8 characters");
        this.password = value;
    }

    @Override
    public String value() {
        return password;
    }
}
