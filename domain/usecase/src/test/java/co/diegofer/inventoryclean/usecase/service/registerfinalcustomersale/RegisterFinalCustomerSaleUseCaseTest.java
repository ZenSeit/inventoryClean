package co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.events.FinalCustomerSaleRegistered;
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
class RegisterFinalCustomerSaleUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus eventBus;

    private RegisterFinalCustomerSaleUseCase registerFinalCustomerSaleUseCase;

    private BranchAggregate savedBranch;

    private Flux<DomainEvent> events;

    @BeforeEach
    void setUp() {
        registerFinalCustomerSaleUseCase = new RegisterFinalCustomerSaleUseCase(repository, eventBus);
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
        RegisterFinalCustomerSaleCommand registerFinalCustomerSaleCommand = new RegisterFinalCustomerSaleCommand();
        registerFinalCustomerSaleCommand.setBranchId("root");
        registerFinalCustomerSaleCommand.setProducts(productSaleList);

        when(repository.findById(Mockito.any())).thenReturn(events);
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new FinalCustomerSaleRegistered()));

        // Act
        StepVerifier.create(registerFinalCustomerSaleUseCase.apply(Mono.just(registerFinalCustomerSaleCommand)))
                .expectNextMatches(event -> event instanceof FinalCustomerSaleRegistered)
                .verifyComplete();
    }

    @Test
    void notEnoughStock(){
        List<ProductSale> productSaleList = new ArrayList<>();
        productSaleList.add(new ProductSale("productID", "product", 10));
        RegisterFinalCustomerSaleCommand registerFinalCustomerSaleCommand = new RegisterFinalCustomerSaleCommand();
        registerFinalCustomerSaleCommand.setBranchId("root");
        registerFinalCustomerSaleCommand.setProducts(productSaleList);

        when(repository.findById(Mockito.any())).thenReturn(events);

        assertThrows(IllegalArgumentException.class, () -> {
            registerFinalCustomerSaleUseCase.apply(Mono.just(registerFinalCustomerSaleCommand))
                    .blockLast();
        });
    }


}