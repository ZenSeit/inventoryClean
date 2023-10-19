package co.diegofer.inventoryclean.model.commands;

import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.generic.Command;

import java.util.List;

public class BuyProductCommand extends Command {

    //private String productId;
    private String branchId;
    //private Integer quantityToAdd;
    List<ProductToAdd> products;

    public BuyProductCommand() {
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
