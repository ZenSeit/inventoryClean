package co.diegofer.inventoryclean.usecase.storage.getallproducts;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    public Flux<Product> apply() {
        return productRepository.findAllProducts();
    }

}
