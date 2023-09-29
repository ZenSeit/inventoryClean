package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class LastName implements ValueObject<String> {

    private final String lastName;

    public LastName(String value) {
        if(value == null || value.length() <3) throw new IllegalArgumentException("Last name cannot be null or empty");
        this.lastName = value;
    }

    @Override
    public String value() {
        return lastName;
    }

}
