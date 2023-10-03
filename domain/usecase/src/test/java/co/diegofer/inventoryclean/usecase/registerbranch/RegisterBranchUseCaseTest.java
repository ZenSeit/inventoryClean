package co.diegofer.inventoryclean.usecase.registerbranch;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.commands.RegisterBranchCommand;
import co.diegofer.inventoryclean.model.commands.custom.LocationCommand;
import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus eventBus;

    private RegisterBranchUseCase registerBranchUseCase;

    @BeforeEach
    void setUp() {
        registerBranchUseCase = new RegisterBranchUseCase(branchRepository, repository, eventBus);
    }


    @Test
    void successfulScenario() {
        RegisterBranchCommand registerBranchCommand = new RegisterBranchCommand(
                "Branch name",
                new LocationCommand("Location","country")
        );

        Branch savedBranch = new Branch("Branch name", "Location, country");

        when(branchRepository.saveABranch(savedBranch)).thenReturn(Mono.just(savedBranch));
        when(repository.saveEvent(Mockito.any())).thenReturn(Mono.just(new BranchCreated()));

        // Act
        StepVerifier.create(registerBranchUseCase.apply(Mono.just(registerBranchCommand)))
                .expectNextCount(1)
                .verifyComplete();



    }
}