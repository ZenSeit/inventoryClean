package co.diegofer.inventoryclean.usecase.storage.getallbranches;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllBranchesUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    private GetAllBranchesUseCase getAllBranchesUseCase;

    @BeforeEach
    void setUp() {
        getAllBranchesUseCase = new GetAllBranchesUseCase(branchRepository);
    }

    @Test
    void apply() {

        List<Branch> sampleBranches = new ArrayList<>();
        sampleBranches.add(new Branch("Branch1","Location1"));
        sampleBranches.add(new Branch("Branch2","Location2"));


        when(branchRepository.findAllBranches()).thenReturn(Flux.fromIterable(sampleBranches));

        // Act
        Flux<Branch> result = getAllBranchesUseCase.apply();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

    }


}