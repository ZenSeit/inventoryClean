package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.generic.Entity;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.*;

public class ProductEntity extends Entity<ProductId> {

    private Name name;
    private Category category;
    private Description description;
    private InventoryStock inventoryStock;
    private Price price;

    public ProductEntity(ProductId id, Name name, Category category, Description description, InventoryStock inventoryStock, Price price) {
        super(id);
        this.name = name;
        this.category = category;
        this.description = description;
        this.inventoryStock = inventoryStock;
        this.price = price;
    }

    public Name name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public Description description() {
        return description;
    }

    public InventoryStock inventoryStock() {
        return inventoryStock;
    }

    public Price price() {
        return price;
    }

    public void setInventoryStock(InventoryStock inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    public void addStock(InventoryStock inventoryStock){
        this.inventoryStock = new InventoryStock(this.inventoryStock.value() + inventoryStock.value());
    }
}
