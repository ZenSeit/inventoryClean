package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.r2dbc.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ObjectMapper mapper;

    private final DatabaseClient dbClient;

    private final ProductRepositoryR2dbc productRepository;

    @Override
    public Mono<Product> saveAProduct(Product product) {
        //String newId = UUID.randomUUID().toString();
        ProductData productData = mapper.map(product, ProductData.class);
        //productData.setId(newId);

        return productRepository.saveProduct(productData.getId(),productData.getName(),productData.getDescription(),
                productData.getInventoryStock(),productData.getPrice(),productData.getCategory(),productData.getBranchId()).thenReturn(
                mapper.map(productData, Product.class))
                .onErrorMap(DataIntegrityViolationException.class,e -> new DataIntegrityViolationException("Error creating product: "+e.getMessage()));
    }

    @Override
    public Flux<Product> findProductsByBranch(String branchId) {
        return productRepository.findByBranchId(branchId)
                .switchIfEmpty(Mono.empty())
                .map(product -> mapper.map(product, Product.class))
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("Branch not found")));
    }

    @Override
    public Flux<Product> findAllProducts() {
        return productRepository.findAll()
                .map(product -> mapper.map(product, Product.class));
    }

    @Override
    public Mono<List<ProductToAdd>> addStock(List<ProductToAdd> productsToAdd) {

        List<ProductToAdd> productsAddedList = new ArrayList<>();

        for(ProductToAdd productRequested: productsToAdd) {
            productRepository.findById(productRequested.getProductId())
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Product with id: " + productRequested.getProductId() + " was not found")))
                    .flatMap(product -> {
                        product.setInventoryStock(product.getInventoryStock()+productRequested.getQuantity());
                        productsAddedList.add(productRequested);
                        return productRepository.save(product);
                    }).map(product -> mapper.map(product, Product.class)).subscribe();

        }

        return Mono.just(productsAddedList);



    }

    @Override
    public Mono<List<ProductSale>> reduceStock(List<ProductSale> productsRequested) {
        List<ProductSale> productsStockReducedList = new ArrayList<>();

        for(ProductSale productRequested: productsRequested) {
            productRepository
                    .findById(productRequested.getId())
                    .switchIfEmpty(Mono.error(new Throwable("There is no enough stock")))
                    .flatMap(product -> {
                        if (product.getInventoryStock() >= productRequested.getQuantity()) {
                            product.setInventoryStock(product.getInventoryStock() - productRequested.getQuantity());
                            productsStockReducedList.add(productRequested);
                            return productRepository.save(product);
                        } else return Mono.error(new Throwable("There is no enough stock"));
                    }).subscribe();

        }
        return Mono.just(productsStockReducedList);
    }
}
