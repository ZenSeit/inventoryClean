package co.diegofer.inventoryclean.usecase.storage.getusersbybranch;

import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
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
class GetUsersByBranchUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private GetUsersByBranchUseCase getUsersByBranchUseCase;

    @BeforeEach
    void setUp() {
        getUsersByBranchUseCase = new GetUsersByBranchUseCase(userRepository);
    }

    @Test
    void apply() {

        List<User> users = List.of(new User("userId","name","lastName","email","password","branchId"),
                new User("userId2","name2","lastName2","email2","password2","branchId"));

        when(userRepository.getUsersByBranch("branchId")).thenReturn(Flux.fromIterable(users));

        Flux<User> result = getUsersByBranchUseCase.apply("branchId");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

    }

}