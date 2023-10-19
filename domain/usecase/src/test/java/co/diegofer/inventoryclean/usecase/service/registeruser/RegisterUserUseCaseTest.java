package co.diegofer.inventoryclean.usecase.service.registeruser;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.events.UserAdded;
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
class RegisterUserUseCaseTest {

    @Mock
    private DomainEventRepository repository;
    @Mock
    private EventBus eventBus;

    private RegisterUserUseCase registerUserUseCase;

    private Flux<DomainEvent> events;



    @BeforeEach
    void setUp() {

        registerUserUseCase = new RegisterUserUseCase(repository, eventBus);

        BranchAggregate savedBranch = new BranchAggregate(BranchId.of("root"), new Name("name"), new Location("location"));
        events = Flux.fromIterable(savedBranch.getUncommittedChanges());
    }

    @Test
    void apply() {

        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setBranchId("root");
        registerUserCommand.setName("Diego");
        registerUserCommand.setLastName("Becerra");
        registerUserCommand.setEmail("diego@correo.com");
        registerUserCommand.setPassword("12345678");
        registerUserCommand.setRole("ADMIN");
        registerUserCommand.setBranchId("root");

        when(repository.findById(Mockito.any())).thenReturn(events);
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new UserAdded()));

        StepVerifier.create(registerUserUseCase.apply(Mono.just(registerUserCommand)))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void invalidRole(){

        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setBranchId("root");
        registerUserCommand.setName("Diego");
        registerUserCommand.setLastName("Becerra");
        registerUserCommand.setEmail("diego@correo.com");
        registerUserCommand.setPassword("12345678");
        registerUserCommand.setRole("INVALID");
        registerUserCommand.setBranchId("root");

        when(repository.findById(Mockito.any())).thenReturn(events);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            registerUserUseCase.apply(Mono.just(registerUserCommand))
                    .blockLast();
        });
    }

}