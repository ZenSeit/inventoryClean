package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.generic.DomainEvent;

import java.util.List;

public class ResellerCustomerSaleRegistered extends DomainEvent {

    List<ProductSale> products;
    double total;

    String sellType;

    public ResellerCustomerSaleRegistered() {
        super("co.diegofer.inventoryclean.model.events.ResellerCustomerSaleRegistered");
    }

    public ResellerCustomerSaleRegistered(List<ProductSale> products, double total) {
        super("co.diegofer.inventoryclean.model.events.ResellerCustomerSaleRegistered");
        this.products = products;
        this.total = total;
        this.sellType = "ResellerCustomerSale";
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
