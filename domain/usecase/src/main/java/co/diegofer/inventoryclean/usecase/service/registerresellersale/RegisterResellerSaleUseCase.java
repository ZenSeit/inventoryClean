package co.diegofer.inventoryclean.usecase.service.registerresellersale;


import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.RegisterResellerCustomerSaleCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.product.Price;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RegisterResellerSaleUseCase extends UserCaseForCommand<RegisterResellerCustomerSaleCommand> {

    private final DomainEventRepository repository;

    private final EventBus eventBus;

    public RegisterResellerSaleUseCase(DomainEventRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }


    @Override
    public Flux<DomainEvent> apply(Mono<RegisterResellerCustomerSaleCommand> registerResellerCustomerSaleCommandMono) {
        return registerResellerCustomerSaleCommandMono.flatMapMany(command -> repository.findById(command.getBranchId())
                .collectList()
                .flatMapMany(events -> {
                            BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()), events);
                            branch.registerResellerCustomerSale(
                                    command.getProducts(),
                                    new Price(branch.calculateTotal(command.getProducts()) * 0.7)
                            );

                            return Flux.fromIterable(branch.getUncommittedChanges());
                        }
                        )
                .map(event -> {
                    eventBus.publish(event);
                    return event;
                }).flatMap(repository::saveEvent)
        );
    }
}
