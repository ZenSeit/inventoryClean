package co.diegofer.inventoryclean.model.commands;


import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.generic.Command;

import java.util.List;

public class BuyProductCommand extends Command {

    private String branchId;
    List<ProductToAdd> products;

    public BuyProductCommand() {
    }

    public BuyProductCommand(String branchId, List<ProductToAdd> products) {
        this.branchId = branchId;
        this.products = products;
    }

    public List<ProductToAdd> getProducts() {
        return products;
    }
    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public void setProducts(List<ProductToAdd> products) {
        this.products = products;
    }
}
