package co.diegofer.inventoryclean.usecase;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.generic.DomainUpdater;

public class MySQLUpdater extends DomainUpdater {

    private final BranchRepository branchRepository;

    public MySQLUpdater(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;

        listen((BranchCreated event) -> {
            Branch branch = new Branch(event.aggregateRootId(), event.getName(), event.getLocation());
            branchRepository.saveABranch(branch).subscribe();
        });
    }

}
