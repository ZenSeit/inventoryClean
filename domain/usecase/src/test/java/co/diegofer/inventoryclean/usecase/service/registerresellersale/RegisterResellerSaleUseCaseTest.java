package co.diegofer.inventoryclean.usecase.service.registerresellersale;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.RegisterResellerCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.events.FinalCustomerSaleRegistered;
import co.diegofer.inventoryclean.model.events.ResellerCustomerSaleRegistered;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.Category;
import co.diegofer.inventoryclean.model.values.product.Description;
import co.diegofer.inventoryclean.model.values.product.Price;
import co.diegofer.inventoryclean.model.values.product.ProductId;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterResellerSaleUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus eventBus;

    private RegisterResellerSaleUseCase registerResellerSaleUseCase;

    private BranchAggregate savedBranch;

    private Flux<DomainEvent> events;

    @BeforeEach
    void setUp() {
        registerResellerSaleUseCase = new RegisterResellerSaleUseCase(repository, eventBus);
        List<ProductToAdd> productsToAddStock = new ArrayList<>();
        productsToAddStock.add(new ProductToAdd("productID", 5));
        savedBranch = new BranchAggregate(BranchId.of("root"), new Name("name"), new Location("location"));
        savedBranch.addProduct(ProductId.of("productID"), new Name("proName"), new Category("General"),
                new Description("Descripcion"), new Price(1000));
        savedBranch.addStockToProduct(productsToAddStock);
        events = Flux.fromIterable(savedBranch.getUncommittedChanges());
    }

    @Test
    void successful(){
        List<ProductSale> productSaleList = new ArrayList<>();
        productSaleList.add(new ProductSale("productID", "product", 2));
        RegisterResellerCustomerSaleCommand registerResellerCustomerSaleCommand = new RegisterResellerCustomerSaleCommand();
        registerResellerCustomerSaleCommand.setBranchId("root");
        registerResellerCustomerSaleCommand.setProducts(productSaleList);

        when(repository.findById(Mockito.any())).thenReturn(events);
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new ResellerCustomerSaleRegistered()));

        // Act
        StepVerifier.create(registerResellerSaleUseCase.apply(Mono.just(registerResellerCustomerSaleCommand)))
                .expectNextMatches(event -> event instanceof ResellerCustomerSaleRegistered)
                .verifyComplete();
    }

    @Test
    void notEnoughStock(){
        List<ProductSale> productSaleList = new ArrayList<>();
        productSaleList.add(new ProductSale("productID", "product", 10));
        RegisterResellerCustomerSaleCommand registerResellerCustomerSaleCommand = new RegisterResellerCustomerSaleCommand();
        registerResellerCustomerSaleCommand.setBranchId("root");
        registerResellerCustomerSaleCommand.setProducts(productSaleList);

        when(repository.findById(Mockito.any())).thenReturn(events);

        assertThrows(IllegalArgumentException.class, () -> {
            registerResellerSaleUseCase.apply(Mono.just(registerResellerCustomerSaleCommand))
                    .blockLast();
        });
    }

}