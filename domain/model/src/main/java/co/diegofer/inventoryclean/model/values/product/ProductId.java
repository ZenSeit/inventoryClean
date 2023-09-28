package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.Identity;

public class ProductId extends Identity {

    public ProductId(){
    }

    private ProductId(String uuid){
        super(uuid);
    }

    public static ProductId of(String uuid){
        return new ProductId(uuid);
    }
}
