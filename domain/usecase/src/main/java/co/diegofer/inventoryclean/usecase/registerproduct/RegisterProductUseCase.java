package co.diegofer.inventoryclean.usecase.registerproduct;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> apply(Product product) {
        return productRepository.saveAProduct(product);
    }


}
