package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.generic.DomainEvent;

import java.time.LocalDateTime;
import java.util.List;

public class ResellerCustomerSaleRegistered extends DomainEvent {

    List<ProductSale> products;
    double total;
    String sellType;

    LocalDateTime date;

    public ResellerCustomerSaleRegistered() {
        super("co.diegofer.inventoryclean.model.events.ResellerCustomerSaleRegistered");
    }

    public ResellerCustomerSaleRegistered(List<ProductSale> products, double total) {
        super("co.diegofer.inventoryclean.model.events.ResellerCustomerSaleRegistered");
        this.products = products;
        this.total = total;
        this.sellType = "ResellerCustomerSale";
        this.date = LocalDateTime.now();
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public String getSellType() {
        return sellType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
