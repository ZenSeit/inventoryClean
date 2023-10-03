package co.diegofer.inventoryclean.model.values.user;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class LastName implements ValueObject<String> {

    private final String lastName;

    public LastName(String value) {
        if(value == null || value.length() <2) throw new IllegalArgumentException("Last name cannot be empty or less than 2");
        this.lastName = value;
    }

    @Override
    public String value() {
        return lastName;
    }

}
