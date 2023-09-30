package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.generic.DomainEvent;

import java.util.List;

public class FinalCustomerSaleRegistered extends DomainEvent {

    List<ProductSale> products;
    int total;

    public FinalCustomerSaleRegistered() {
        super("co.diegofer.inventoryclean.model.events.ProductAdded");
    }

    public FinalCustomerSaleRegistered(List<ProductSale> products, int total) {
        super("co.diegofer.inventoryclean.model.events.ProductAdded");
        this.products = products;
        this.total = total;
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
