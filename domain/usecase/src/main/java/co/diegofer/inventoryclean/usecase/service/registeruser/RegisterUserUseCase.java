package co.diegofer.inventoryclean.usecase.service.registeruser;

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

import java.util.UUID;


public class RegisterUserUseCase extends UserCaseForCommand<RegisterUserCommand> {


    private final DomainEventRepository repository;
    private final EventBus eventBus;

    public RegisterUserUseCase(DomainEventRepository repository, EventBus eventBus) {
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

                        BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()), events);
                        branch.addUser(
                                UserId.of(UUID.randomUUID().toString()),
                                name,
                                lastName,
                                email,
                                password,
                                role
                        );

                        return Flux.fromIterable(branch.getUncommittedChanges());

                })
                .map(event -> {
                    eventBus.publish(event);
                    return event;
                }).flatMap(repository::saveEvent)
        );
    }

}
