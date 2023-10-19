package co.diegofer.inventoryclean.usecase.storage.getallproducts;

import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    private GetAllProductsUseCase getAllProductsUseCase;

    @BeforeEach
    void setUp() {
        getAllProductsUseCase = new GetAllProductsUseCase(productRepository);
    }

    @Test
    void apply() {

        List<Product> sampleProducts = List.of(
                new Product("product1", "name1", 0, 1000, "General", "branch1"),
                new Product("product2", "name2", 0, 1000, "General", "branch2")
        );

        when(productRepository.findAllProducts()).thenReturn(Flux.fromIterable(sampleProducts));

        Flux<Product> result = getAllProductsUseCase.apply();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

    }

}