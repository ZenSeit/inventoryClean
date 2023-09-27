package co.diegofer.inventoryclean.model.product.gateways;

import co.diegofer.inventoryclean.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    public Mono<Product> saveAProduct(Product product);
    public Flux<Product> findProductsByBranch(String branchId);
    public Mono<Product> addStock(String productId, Integer quantity);
    public Mono<Product> reduceStock(String productId, Integer quantity);
}
