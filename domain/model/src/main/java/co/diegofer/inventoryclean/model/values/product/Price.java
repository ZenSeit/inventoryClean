package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class Price implements ValueObject<Double> {

    private final double price;

    public Price(double price) {
        if(price < 0) throw new IllegalArgumentException("Price cannot be less than 0");
        this.price = price;
    }


    @Override
    public Double value() {
        return price;
    }
}
