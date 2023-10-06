package co.diegofer.inventoryclean.usecase.storage.getbranchbyid;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetBranchByIdUseCase {

    private final BranchRepository branchRepository;

    public Mono<Branch> apply(String branchId) {
        return branchRepository.findBranchById(branchId);
    }
}
