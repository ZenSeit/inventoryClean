package co.diegofer.inventoryclean.usecase.storage.getproductsbybranchid;

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
class GetProductsByBranchIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    private GetProductsByBranchIdUseCase getProductsByBranchIdUseCase;


    @BeforeEach
    void setUp() {
        getProductsByBranchIdUseCase = new GetProductsByBranchIdUseCase(productRepository);
    }

    @Test
    void apply() {

        List<Product> products = List.of(new Product("productId","name","description",0,1000,"general","branchId"),
                new Product("productId2","name2","description",0,1000,"general","branchId"));

        when(productRepository.findProductsByBranch("branchId")).thenReturn(Flux.fromIterable(products));

        Flux<Product> result = getProductsByBranchIdUseCase.apply("branchId");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

    }

}