package co.diegofer.inventoryclean.usecase.service.loginuser;

import co.diegofer.inventoryclean.model.commands.LoginCommand;
import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setUp() {
        loginUserUseCase = new LoginUserUseCase(userRepository);
    }

    @Test
    void apply() {

        LoginCommand loginCommand = new LoginCommand("test@email.com", "testpassword");

        AuthResponse authResponse = new AuthResponse("accessToken");

        when(userRepository.authenticate(Mockito.any(AuthRequest.class))).thenReturn(Mono.just(authResponse));

        // Act
        Mono<AuthResponse> result = loginUserUseCase.apply(Mono.just(loginCommand));

        StepVerifier.create(result)
                .expectNext(authResponse)
                .verifyComplete();

    }

}