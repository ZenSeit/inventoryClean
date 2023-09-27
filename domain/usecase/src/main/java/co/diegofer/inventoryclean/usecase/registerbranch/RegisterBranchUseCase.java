package co.diegofer.inventoryclean.usecase.registerbranch;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterBranchUseCase {

    private final BranchRepository branchRepository;

    public Mono<Branch> apply(Branch branch) {
        return branchRepository.saveABranch(branch);
    }

}
