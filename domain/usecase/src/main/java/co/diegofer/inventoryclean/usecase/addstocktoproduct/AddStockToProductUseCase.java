package co.diegofer.inventoryclean.usecase.addstocktoproduct;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddStockToProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> apply(String productId, Integer stock) {
        return productRepository.addStock(productId, stock);
    }

}
