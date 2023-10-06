package co.diegofer.inventoryclean.model.invoice;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceData {

    private String id;
    private List<ProductSale> products;
    private double total;
    private LocalDateTime date;
    private String sellType;
    private String branchId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSale> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
