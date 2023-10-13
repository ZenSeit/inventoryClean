package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.generic.DomainEvent;

import java.util.List;

public class StockAdded extends DomainEvent {

    private List<ProductToAdd> products;

    public StockAdded() {
        super("co.diegofer.inventoryclean.model.events.StockAdded");
    }

    public StockAdded(List<ProductToAdd> products) {
        super("co.diegofer.inventoryclean.model.events.StockAdded");
        this.products = products;
    }

    public List<ProductToAdd> getProducts() {
        return products;
    }
}
