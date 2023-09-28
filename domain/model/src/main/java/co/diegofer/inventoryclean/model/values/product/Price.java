package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Price implements ValueObject<Integer> {

    private final Integer price;

    public Price(Integer price) {
        if(price == null || price < 0) throw new IllegalArgumentException("Price cannot be null or empty");
        this.price = price;
    }

    @Override
    public Integer value() {
        return price;
    }
}
