package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Description implements ValueObject<String> {

    private final String description;

    public Description(String description) {
        if(description == null || description.length() <3) throw new IllegalArgumentException("Description cannot be null or empty");
        this.description = description;
    }
    @Override
    public String value() {
        return description;
    }
}
