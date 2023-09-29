package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.r2dbc.data.ProductData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryR2dbc extends ReactiveCrudRepository<ProductData, String> {

    @Query("INSERT INTO Product(id, name, description, inventory_stock, price, category, branch_id) VALUES(:id, :name, :description, :inventoryStock, :price, :category, :branchId)")
    Mono<ProductData> saveProduct(@Param("id") String id,
                                  @Param("name") String name,
                                  @Param("description") String description,
                                  @Param("inventoryStock") Integer inventoryStock,
                                  @Param("price") float price,
                                  @Param("category") String category,
                                  @Param("branchId") String branchId);

    Flux<Product> findByBranchId(String branchId);
}
