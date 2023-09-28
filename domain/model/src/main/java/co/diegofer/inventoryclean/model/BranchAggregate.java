package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.generic.AggregateRoot;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.branch.Name;

public class BranchAggregate extends AggregateRoot<BranchId> {

    protected Name name;
    protected Location location;

    public BranchAggregate(BranchId id, Name name, Location location) {
        super(id);
        subscribe(new BranchChange(this));
        appendChange(new BranchCreated(name.value(), location.value())).apply();
    }

    private BranchAggregate(BranchId id) {
        super(id);
        subscribe(new BranchChange(this));
    }



}
