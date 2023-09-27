package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.r2dbc.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ObjectMapper mapper;

    private final DatabaseClient dbClient;

    @Override
    public Mono<Product> saveAProduct(Product product) {
        String newId = UUID.randomUUID().toString();
        ProductData productData = mapper.map(product, ProductData.class);
        dbClient.sql("INSERT INTO Product(id, name, description, inventory_stock, category, branch_id) VALUES(:id, :name, :description, :inventoryStock, :category, :branchId)")
                .bind("id", newId)
                .bind("name", productData.getName())
                .bind("description", productData.getDescription())
                .bind("inventoryStock", productData.getInventoryStock())
                .bind("category", productData.getCategory())
                .bind("branchId", productData.getBranchId())
                .fetch()
                .one()
                .subscribe();
        product.setId(newId);
        return Mono.just(product);
    }

    @Override
    public Flux<Product> findProductsByBranch(String branchId) {
        return null;
    }

    @Override
    public Mono<Product> addStock(String productId, Integer quantity) {
        return null;
    }

    @Override
    public Mono<Product> reduceStock(String productId, Integer quantity) {
        return null;
    }
}
