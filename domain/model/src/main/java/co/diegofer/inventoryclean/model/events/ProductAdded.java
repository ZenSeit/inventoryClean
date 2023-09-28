package co.diegofer.inventoryclean.model.events;

import co.diegofer.inventoryclean.model.generic.DomainEvent;

public class ProductAdded extends DomainEvent {

    private String productId;
    private String name;
    private String category;
    private String description;
    private Integer price;

    public ProductAdded() {
        super("co.diegofer.inventoryclean.model.events.ProductAdded");
    }

    public ProductAdded(String productId, String name, String category, String description, Integer price) {
        super("co.diegofer.inventoryclean.model.events.ProductAdded");
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }


    public Integer getPrice() {
        return price;
    }
}
