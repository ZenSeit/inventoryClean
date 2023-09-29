package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Password implements ValueObject<String> {

    private final String password;

    public Password(String value){
        this.password = value;
    }

    @Override
    public String value() {
        return password;
    }
}
