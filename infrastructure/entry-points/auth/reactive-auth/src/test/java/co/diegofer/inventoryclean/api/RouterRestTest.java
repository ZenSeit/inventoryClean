package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.commands.LoginCommand;
import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.usecase.service.loginuser.LoginUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(new RouterRest().loginUser(new Handler(loginUserUseCase)))
                .configureClient()
                .baseUrl("http://localhost:8083")
                .build();
    }

    @Test
    void loginUser() {

        String token = "accessToken";

        LoginCommand loginCommand = new LoginCommand("username", "password");

        AuthResponse authResponse = new AuthResponse(token);

        when(loginUserUseCase.apply(any(Mono.class))).thenReturn(Mono.just(authResponse));

        webTestClient.post()
                .uri("api/v1/auth/login")
                .body(Mono.just(loginCommand), LoginCommand.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class)
                .value(response -> {
                    assertEquals(token, response.getToken());
                });

    }
}