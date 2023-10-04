package co.diegofer.inventoryclean.usecase.service.registerbranch;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.commands.RegisterBranchCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.branch.Location;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class RegisterBranchUseCase extends UserCaseForCommand<RegisterBranchCommand> {

    private final DomainEventRepository repository;

    private final EventBus eventBus;

    public RegisterBranchUseCase(DomainEventRepository repository, EventBus eventBus) {

        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<RegisterBranchCommand> registerBranchCommandMono) {

        return registerBranchCommandMono.flatMapMany(command -> {
                    Name name = new Name(command.getName());
                    Location location = new Location(command.getLocation());

                                BranchAggregate branchAggregate = new BranchAggregate(
                                        BranchId.of(UUID.randomUUID().toString()),
                                        name,
                                        location
                                );

                                return Flux.fromIterable(branchAggregate.getUncommittedChanges());

                }

        ).map(event -> {
            eventBus.publish(event);
            return event;
        }).flatMap(repository::saveEvent);
    }


}
