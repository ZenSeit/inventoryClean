package co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.product.Price;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class RegisterFinalCustomerSaleUseCase extends UserCaseForCommand<RegisterFinalCustomerSaleCommand> {

    private final DomainEventRepository repository;

    private final EventBus eventBus;

    public RegisterFinalCustomerSaleUseCase(DomainEventRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<RegisterFinalCustomerSaleCommand> registerFinalCustomerSaleCommandMono) {
        return registerFinalCustomerSaleCommandMono.flatMapMany(command -> repository.findById(command.getBranchId())
                .collectList()
                        .flatMapMany(
                                events ->{
                                    BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()), events);
                                    branch.registerFinalCustomerSale(
                                            command.getProducts(),
                                            new Price(branch.calculateTotal(command.getProducts()) * 0.95)
                                    );

                                    return Flux.fromIterable(branch.getUncommittedChanges());
                                }
                        ))
                .map(event -> {
                    eventBus.publish(event);
                    return event;
                }).flatMap(repository::saveEvent);
    }
}
