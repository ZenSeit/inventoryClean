package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.events.ProductAdded;
import co.diegofer.inventoryclean.model.events.UserAdded;
import co.diegofer.inventoryclean.model.generic.EventChange;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.*;
import co.diegofer.inventoryclean.model.values.user.Email;
import co.diegofer.inventoryclean.model.values.user.Password;
import co.diegofer.inventoryclean.model.values.user.Role;
import co.diegofer.inventoryclean.model.values.user.UserId;

import java.util.ArrayList;

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

    }


}
