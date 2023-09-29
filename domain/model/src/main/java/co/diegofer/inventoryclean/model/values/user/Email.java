package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Email implements ValueObject<String> {

    private final String email;

    public Email(String value){
        this.email = value;
    }

    @Override
    public String value() {
        return email;
    }
}
