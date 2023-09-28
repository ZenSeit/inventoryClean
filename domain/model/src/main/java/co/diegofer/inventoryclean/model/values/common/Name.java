package co.diegofer.inventoryclean.model.values.common;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Name implements ValueObject<String> {

    private final String name;

    public Name(String value) {
        if(value == null || value.length() <3) throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = value;
    }

    @Override
    public String value() {
        return name;
    }

}
