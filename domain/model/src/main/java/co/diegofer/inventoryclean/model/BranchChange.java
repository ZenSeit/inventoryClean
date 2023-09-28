package co.diegofer.inventoryclean.model;

import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.generic.EventChange;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.branch.Name;

public class BranchChange extends EventChange {

    public BranchChange(BranchAggregate branchAggregate) {

        apply((BranchCreated event) -> {
            branchAggregate.name = new Name(event.getName());
            branchAggregate.location = new Location(event.getLocation());
        });

    }

}
