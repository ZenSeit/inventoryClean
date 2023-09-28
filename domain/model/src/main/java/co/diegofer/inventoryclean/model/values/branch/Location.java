package co.diegofer.inventoryclean.model.values.branch;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Location implements ValueObject<String>{
    private final String location;

    public Location(String value) {
        this.location = value;
    }

    @Override
    public String value() {
        return location;
    }

}
