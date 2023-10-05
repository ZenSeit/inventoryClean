package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Email implements ValueObject<String> {

    private final String email;

    public Email(String value){
        if(!isValidEmail(value)) throw new IllegalArgumentException("Email is not valid");
        this.email = value;
    }

    @Override
    public String value() {
        return email;
    }

    private boolean isValidEmail(String email){
        return true;//.matches("^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$");
    }
}
