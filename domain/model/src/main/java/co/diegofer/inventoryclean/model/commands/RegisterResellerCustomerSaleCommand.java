package co.diegofer.inventoryclean.model.commands;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.generic.Command;

import java.util.List;

public class RegisterResellerCustomerSaleCommand extends Command {

    List<ProductSale> products;
    String branchId;

    public RegisterResellerCustomerSaleCommand() {
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
