package co.diegofer.inventoryclean.usecase.getproductsbybranchid;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetProductsByBranchIdUseCase {

    private final ProductRepository productRepository;

    public Flux<Product> apply(String branchId) {
        return productRepository.findProductsByBranch(branchId);
    }
}
