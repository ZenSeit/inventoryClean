package co.diegofer.inventoryclean.usecase.service.loginuser;

import co.diegofer.inventoryclean.model.commands.LoginCommand;
import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

public class LoginUserUseCase {

    private final UserRepository userRepository;

    public LoginUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<AuthResponse> apply(Mono<LoginCommand> command) {
        return command.flatMap(command1 -> userRepository.authenticate(
                new AuthRequest(command1.getEmail(), command1.getPassword())
        ));
    }
}
