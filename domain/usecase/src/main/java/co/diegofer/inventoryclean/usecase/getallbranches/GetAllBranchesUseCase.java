package co.diegofer.inventoryclean.usecase.getallbranches;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllBranchesUseCase {

    private final BranchRepository branchRepository;

    public Flux<Branch> apply() {
        return branchRepository.findAllBranches();
    }
}
