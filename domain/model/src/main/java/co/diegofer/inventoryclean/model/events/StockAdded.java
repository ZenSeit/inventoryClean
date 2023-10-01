package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.generic.DomainEvent;

public class StockAdded extends DomainEvent {

    private String productId;
    private Integer quantityToAdd;

    public StockAdded() {
        super("co.diegofer.inventoryclean.model.events.StockAdded");
    }

    public StockAdded(String productId, Integer quantityToAdd) {
        super("co.diegofer.inventoryclean.model.events.StockAdded");
        this.productId = productId;
        this.quantityToAdd = quantityToAdd;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantityToAdd() {
        return quantityToAdd;
    }


}
