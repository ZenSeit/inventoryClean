package co.diegofer.inventoryclean.usecase.registerfinalcustomersale;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RegisterFinalCustomerSaleUseCase extends UserCaseForCommand<RegisterFinalCustomerSaleCommand> {

    private final ProductRepository productRepository;

    private final DomainEventRepository repository;

    public RegisterFinalCustomerSaleUseCase(ProductRepository productRepository, DomainEventRepository repository) {
        this.productRepository = productRepository;
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<RegisterFinalCustomerSaleCommand> registerFinalCustomerSaleCommandMono) {
        return registerFinalCustomerSaleCommandMono.flatMapMany(command -> repository.findById(command.getBranchId())
                .collectList()
                .flatMapMany(events -> productRepository.reduceStock(command.getProducts())
                        .flatMapMany(

                                productsStockReduced ->{
                                    BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()), events);
                                    branch.registerFinalCustomerSale(
                                            command.getProducts(),
                                            branch.calculateTotal(command.getProducts())
                                    );

                                    return Flux.fromIterable(branch.getUncommittedChanges());
                                }
                        ))
                .map(event -> {
                    //bus.saveEvent(event);
                    return event;
                }).flatMap(repository::saveEvent)
        );
    }
}
