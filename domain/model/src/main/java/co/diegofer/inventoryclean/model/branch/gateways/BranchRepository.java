package co.diegofer.inventoryclean.model.branch.gateways;

import co.diegofer.inventoryclean.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {

    public Mono<Branch> saveABranch(Branch branch);

    public Flux<Branch> findAllBranches();

    public Mono<Branch> findBranchById(String branchId);
}
