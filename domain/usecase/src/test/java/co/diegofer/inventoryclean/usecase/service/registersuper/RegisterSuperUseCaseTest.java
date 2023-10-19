package co.diegofer.inventoryclean.usecase.service.registersuper;

import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.common.RoleEnum;
import co.diegofer.inventoryclean.model.values.user.Email;
import co.diegofer.inventoryclean.model.values.user.LastName;
import co.diegofer.inventoryclean.model.values.user.Password;
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
class RegisterSuperUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private RegisterSuperUseCase registerSuperUseCase;

    @BeforeEach
    void setUp() {
        registerSuperUseCase = new RegisterSuperUseCase(userRepository);
    }

    @Test
    void apply() {

        Name name = new Name("Diego");
        LastName lastName = new LastName("Becerra");
        Email email = new Email("diego@correo.com");
        Password password = new Password("12345678");

        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setName(name.value());
        registerUserCommand.setLastName(lastName.value());
        registerUserCommand.setEmail(email.value());
        registerUserCommand.setPassword(password.value());

        User expectedUser = new User(name.value(), lastName.value(), email.value(),
                password.value(), RoleEnum.SUPER.name(), null);

        when(userRepository.saveASuper(Mockito.any(User.class))).thenReturn(Mono.just(expectedUser));

        StepVerifier.create(registerSuperUseCase.apply(Mono.just(registerUserCommand)))
                .expectNextMatches(user -> user.equals(expectedUser))
                .verifyComplete();
    }


}