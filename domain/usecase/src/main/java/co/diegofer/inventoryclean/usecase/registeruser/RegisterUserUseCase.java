package co.diegofer.inventoryclean.usecase.registeruser;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.user.*;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class RegisterUserUseCase extends UserCaseForCommand<RegisterUserCommand> {

    private final UserRepository userRepository;

    private final DomainEventRepository repository;
    private final EventBus eventBus;

    public RegisterUserUseCase(UserRepository userRepository, DomainEventRepository repository, EventBus eventBus) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.eventBus = eventBus;
    }



    @Override
    public Flux<DomainEvent> apply(Mono<RegisterUserCommand> registerUserCommandMono) {
        return registerUserCommandMono.flatMapMany(command -> repository.findById(command.getBranchId())
                .collectList()
                .flatMapMany(events -> {

                    Name name = new Name(command.getName());
                    LastName lastName = new LastName(command.getLastName());
                    Email email = new Email(command.getEmail());
                    Password password = new Password(command.getPassword());
                    Role role = new Role(command.getRole());


                    return userRepository.saveAUser(new User(
                            name.value(),
                            lastName.value(),
                            email.value(),
                            password.value(),
                            role.value(),
                            command.getBranchId()))
                            .flatMapMany(

                                    savedUser ->{
                                        System.out.println("savedUser: "+savedUser.getName());
                                        BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()), events);
                                        branch.addUser(
                                                UserId.of(savedUser.getId()),
                                                new Name(savedUser.getName()),
                                                new LastName(savedUser.getLastName()),
                                                new Email(savedUser.getEmail()),
                                                new Password(savedUser.getPassword()),
                                                new Role(savedUser.getRole())
                                        );

                                        return Flux.fromIterable(branch.getUncommittedChanges());
                                    }
                            );

                })
                .map(event -> {
                    eventBus.publish(event);
                    return event;
                }).flatMap(repository::saveEvent)
        );
    }

}
