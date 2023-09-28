package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Category implements ValueObject<String> {

    private final String category;

    public Category(String category) {
        if(category == null || category.length()<3) throw new IllegalArgumentException("Category cannot be null or empty");
        this.category = category;
    }

    @Override
    public String value() {
        return category;
    }
}
