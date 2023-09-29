package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.events.ProductAdded;
import co.diegofer.inventoryclean.model.events.UserAdded;
import co.diegofer.inventoryclean.model.generic.AggregateRoot;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.*;
import co.diegofer.inventoryclean.model.values.user.*;

import java.util.List;

public class BranchAggregate extends AggregateRoot<BranchId> {

    protected Name name;
    protected Location location;
    protected List<ProductEntity> products;
    protected List<UserEntity> users;

    public BranchAggregate(BranchId id, Name name, Location location) {
        super(id);
        subscribe(new BranchChange(this));
        appendChange(new BranchCreated(name.value(), location.value())).apply();
    }

    private BranchAggregate(BranchId id) {
        super(id);
        subscribe(new BranchChange(this));
    }

    public static BranchAggregate from(BranchId id, List<DomainEvent> events) {
        BranchAggregate branchAggregate = new BranchAggregate(id);
        events.forEach(branchAggregate::applyEvent);
        return branchAggregate;
    }

    public void addProduct(ProductId productId, Name name, Category category, Description description, Price price) {
        appendChange(new ProductAdded(productId.value(), name.value(), category.value(), description.value(), price.value())).apply();
    }

    public void addUser(UserId userId, Name name, LastName lastName, Email email, Password password, Role role) {
        appendChange(new UserAdded(userId.value(), name.value(), lastName.value(), email.value(), password.value(), role.value())).apply();
    }



}
