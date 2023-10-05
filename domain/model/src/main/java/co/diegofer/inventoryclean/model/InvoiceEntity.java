package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.generic.Entity;
import co.diegofer.inventoryclean.model.values.product.ProductId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceEntity extends Entity<ProductId> {

    private String id;
    private List<ProductSale> products;
    private double total;
    private LocalDateTime date;

    public InvoiceEntity(ProductId id, String id1, List<ProductSale> products, double total, LocalDateTime date) {
        super(id);
        this.id = id1;
        this.products = products;
        this.total = total;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
