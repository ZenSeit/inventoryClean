package co.diegofer.inventoryclean.model.values.branch;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Location implements ValueObject<String>{
    private final String location;

    public Location(String value) {
        if(value == null || value.length() < 6) throw new IllegalArgumentException("Location cannot be null or less than 6 characters");
        this.location = value;
    }

    @Override
    public String value() {
        return location;
    }

}
