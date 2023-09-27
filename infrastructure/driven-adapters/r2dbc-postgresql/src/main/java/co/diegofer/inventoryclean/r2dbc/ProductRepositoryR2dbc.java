package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.r2dbc.data.ProductData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepositoryR2dbc extends ReactiveCrudRepository<ProductData, String> {
}
