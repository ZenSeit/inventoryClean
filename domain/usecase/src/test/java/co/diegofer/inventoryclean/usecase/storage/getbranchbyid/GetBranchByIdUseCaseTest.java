package co.diegofer.inventoryclean.usecase.storage.getbranchbyid;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBranchByIdUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    private GetBranchByIdUseCase getBranchByIdUseCase;

    @BeforeEach
    void setUp() {
        getBranchByIdUseCase = new GetBranchByIdUseCase(branchRepository);
    }

    @Test
    void apply() {

        Branch branch = new Branch("branchId","Branch name", "Location, country");

        when(branchRepository.findBranchById("branchId")).thenReturn(Mono.just(branch));

        Mono<Branch> result = getBranchByIdUseCase.apply("branchId");

        StepVerifier.create(result)
                .expectNextMatches(branch1 -> branch1.getId().equals("branchId"))
                .verifyComplete();

    }

}