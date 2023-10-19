package co.diegofer.inventoryclean.usecase.service.registerproduct;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.AddProductCommand;
import co.diegofer.inventoryclean.model.events.ProductAdded;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterProductUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus eventBus;

    private RegisterProductUseCase registerProductUseCase;

    private Flux<DomainEvent> events;

    @BeforeEach
    void setUp() {
        registerProductUseCase = new RegisterProductUseCase(repository, eventBus);

        BranchAggregate savedBranch = new BranchAggregate(BranchId.of("root"), new Name("name"), new Location("location"));
        events = Flux.fromIterable(savedBranch.getUncommittedChanges());
    }

    @Test
    void apply() {

        AddProductCommand addProductCommand = new AddProductCommand();
        addProductCommand.setBranchId("root");
        addProductCommand.setProductId("productId");
        addProductCommand.setName("Hammer");
        addProductCommand.setCategory("GENERAL");
        addProductCommand.setDescription("example");
        addProductCommand.setPrice(1000);

        when(repository.findById(Mockito.any())).thenReturn(events);
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new ProductAdded()));

        // Act
        StepVerifier.create(registerProductUseCase.apply(Mono.just(addProductCommand)))
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void invalidCategory(){
        AddProductCommand addProductCommand = new AddProductCommand();
        addProductCommand.setBranchId("root");
        addProductCommand.setProductId("productId");
        addProductCommand.setName("Hammer");
        addProductCommand.setCategory("O");
        addProductCommand.setDescription("example");
        addProductCommand.setPrice(1000);

        when(repository.findById(Mockito.any())).thenReturn(events);

        assertThrows(IllegalArgumentException.class, () -> {
            registerProductUseCase.apply(Mono.just(addProductCommand))
                    .blockLast();
        });
    }



}