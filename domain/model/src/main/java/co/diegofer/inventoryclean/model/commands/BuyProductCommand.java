package co.diegofer.inventoryclean.model.commands;

import co.diegofer.inventoryclean.model.generic.Command;

public class BuyProductCommand extends Command {

    private String productId;
    private String branchId;
    private Integer quantity;

    public BuyProductCommand() {
    }


    public String getProductId() {
        return productId;
    }

    public String getBranchId() {
        return branchId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
