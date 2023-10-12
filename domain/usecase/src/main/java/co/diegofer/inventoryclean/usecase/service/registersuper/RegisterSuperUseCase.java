package co.diegofer.inventoryclean.usecase.service.registersuper;

import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.common.RoleEnum;
import co.diegofer.inventoryclean.model.values.user.Email;
import co.diegofer.inventoryclean.model.values.user.LastName;
import co.diegofer.inventoryclean.model.values.user.Password;
import co.diegofer.inventoryclean.model.values.user.Role;
import reactor.core.publisher.Mono;

public class RegisterSuperUseCase {

    private final UserRepository userRepository;

    public RegisterSuperUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(Mono<RegisterUserCommand> command){

        return command.flatMap( command1 -> {
            Name name = new Name(command1.getName());
            LastName lastName = new LastName(command1.getLastName());
            Email email = new Email(command1.getEmail());
            Password password = new Password(command1.getPassword());
            //Role role = new Role(command.getRole());

            User user = new User(name.value(), lastName.value(), email.value(),
                    password.value(), RoleEnum.SUPER.name(), null);

            return userRepository.saveASuper(user);
        });


    }

}
