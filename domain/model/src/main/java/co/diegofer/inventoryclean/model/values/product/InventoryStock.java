package co.diegofer.inventoryclean.model.values.product;

import co.diegofer.inventoryclean.model.generic.ValueObject;

public class InventoryStock implements ValueObject<Integer> {

    private final int inventoryStock;

    public InventoryStock(int inventoryStock) {
        if(inventoryStock < 0) throw new IllegalArgumentException("Inventory stock cannot be negative");
        this.inventoryStock = inventoryStock;
    }

    @Override
    public Integer value() {
        return inventoryStock;
    }
}
