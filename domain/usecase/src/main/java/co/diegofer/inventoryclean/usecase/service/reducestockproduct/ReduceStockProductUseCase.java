package co.diegofer.inventoryclean.usecase.service.reducestockproduct;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReduceStockProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> apply(String branchId, Integer quantity) {
        return null;
    }

}
