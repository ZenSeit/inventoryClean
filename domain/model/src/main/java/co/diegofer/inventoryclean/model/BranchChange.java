package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.events.*;
import co.diegofer.inventoryclean.model.generic.EventChange;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.*;
import co.diegofer.inventoryclean.model.values.user.Email;
import co.diegofer.inventoryclean.model.values.user.Password;
import co.diegofer.inventoryclean.model.values.user.Role;
import co.diegofer.inventoryclean.model.values.user.UserId;

import java.util.ArrayList;
import java.util.Objects;

public class BranchChange extends EventChange {

    public BranchChange(BranchAggregate branchAggregate) {

        apply((BranchCreated event) -> {
            branchAggregate.name = new Name(event.getName());
            branchAggregate.location = new Location(event.getLocation());
            branchAggregate.products = new ArrayList<>();
            branchAggregate.users = new ArrayList<>();
        });

        apply((ProductAdded event) -> {
            branchAggregate.products.add(new ProductEntity(
                    ProductId.of(event.getProductId()),
                    new Name(event.getName()),
                    new Category(event.getCategory()),
                    new Description(event.getDescription()),
                    new InventoryStock(0),
                    new Price(event.getPrice())
            ));
        });

        apply((UserAdded event) -> {
            branchAggregate.users.add(new UserEntity(
                    UserId.of(event.getUserId()),
                    new Name(event.getName()),
                    new Email(event.getEmail()),
                    new Password(event.getPassword()),
                    new Role(event.getRole())
            ));
        });

        apply((StockAdded event) -> {
            for (ProductEntity product: branchAggregate.products) {
                if (product.identity().value().equals(event.getProductId())){
                    product.addStock(new InventoryStock(event.getQuantityToAdd()));
                }
            }
        });

        apply((ResellerCustomerSaleRegistered event) -> {
            for (ProductSale productRequested: event.getProducts()) {
                for (ProductEntity productInBranch: branchAggregate.products) {
                    if (Objects.equals(productRequested.getId(), productInBranch.identity().value())){
                        productInBranch.setInventoryStock(
                                new InventoryStock(
                                        productInBranch.inventoryStock().value() - productRequested.getQuantity()
                                ));
                    }
                }
            }
        });

        apply((FinalCustomerSaleRegistered event) -> {
            for (ProductSale productRequested: event.getProducts()) {
                for (ProductEntity productInBranch: branchAggregate.products) {
                    if (Objects.equals(productRequested.getId(), productInBranch.identity().value())){
                        productInBranch.setInventoryStock(
                                new InventoryStock(
                                        productInBranch.inventoryStock().value() - productRequested.getQuantity()
                                ));
                    }
                }
            }
        });



    }


}
