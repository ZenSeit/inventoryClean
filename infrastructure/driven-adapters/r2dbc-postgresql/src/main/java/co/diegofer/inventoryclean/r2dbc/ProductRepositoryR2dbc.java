package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.r2dbc.data.ProductData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepositoryR2dbc extends ReactiveCrudRepository<ProductData, String> {

    Flux<Product> findByBranchId(String branchId);
}
