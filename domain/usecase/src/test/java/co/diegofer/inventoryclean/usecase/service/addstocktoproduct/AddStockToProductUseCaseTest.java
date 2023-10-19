package co.diegofer.inventoryclean.usecase.service.addstocktoproduct;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.AddProductCommand;
import co.diegofer.inventoryclean.model.commands.BuyProductCommand;
import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.events.StockAdded;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
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
class AddStockToProductUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus eventBus;

    private AddStockToProductUseCase addStockToProductUseCase;

    private Flux<DomainEvent> events;

    private BranchAggregate savedBranch;

    @BeforeEach
    void setUp() {
        addStockToProductUseCase = new AddStockToProductUseCase(repository, eventBus);

        savedBranch = new BranchAggregate(BranchId.of("root"), new Name("name"), new Location("location"));
        events = Flux.fromIterable(savedBranch.getUncommittedChanges());
    }

    @Test
    void apply() {

        List<ProductToAdd> products = new ArrayList<>();
        products.add(new ProductToAdd("productID", 5));
        BuyProductCommand buyProductCommand = new BuyProductCommand();
        buyProductCommand.setBranchId("root");
        buyProductCommand.setProducts(products);

        savedBranch.addProduct(ProductId.of("productID"), new Name("Hammer"), new co.diegofer.inventoryclean.model.values.product.Category("GENERAL"), new Description("description"), new Price(1000));
        events = Flux.fromIterable(savedBranch.getUncommittedChanges());


        when(repository.findById(Mockito.any())).thenReturn(events);
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new StockAdded()));

        // Act
        StepVerifier.create(addStockToProductUseCase.apply(Mono.just(buyProductCommand)))
                .expectNextMatches(event -> event instanceof StockAdded)
                .verifyComplete();
    }



}