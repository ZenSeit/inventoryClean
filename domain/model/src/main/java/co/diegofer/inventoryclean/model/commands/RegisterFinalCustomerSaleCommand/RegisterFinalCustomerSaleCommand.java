package co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand;

import co.diegofer.inventoryclean.model.generic.Command;

import java.util.List;

public class RegisterFinalCustomerSaleCommand extends Command {

    List<ProductSale> products;
    String branchId;

    public RegisterFinalCustomerSaleCommand() {
    }

    public List<ProductSale> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSale> products) {
        this.products = products;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
