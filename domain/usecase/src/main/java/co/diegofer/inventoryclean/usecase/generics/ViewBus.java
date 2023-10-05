package co.diegofer.inventoryclean.usecase.generics;


import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.user.User;

public interface ViewBus {

    void publishBranch(DomainEvent event);
    void publishUser(DomainEvent event);
    void publishProduct(DomainEvent event);
}
