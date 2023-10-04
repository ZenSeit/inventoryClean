package co.diegofer.inventoryclean.usecase.service.registerproduct;

import co.diegofer.inventoryclean.model.BranchAggregate;
import co.diegofer.inventoryclean.model.commands.AddProductCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.model.values.branch.BranchId;
import co.diegofer.inventoryclean.model.values.common.Name;
import co.diegofer.inventoryclean.model.values.product.*;
import co.diegofer.inventoryclean.usecase.generics.DomainEventRepository;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import co.diegofer.inventoryclean.usecase.generics.UserCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RegisterProductUseCase extends UserCaseForCommand<AddProductCommand> {

    private final ProductRepository productRepository;

    private final DomainEventRepository repository;

    private final EventBus eventBus;

    public RegisterProductUseCase(ProductRepository productRepository, DomainEventRepository repository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.repository = repository;
        this.eventBus = eventBus;
    }


    @Override
    public Flux<DomainEvent> apply(Mono<AddProductCommand> addProductCommandMono) {
        return addProductCommandMono.flatMapMany(command -> repository.findById(command.getBranchId())
                .collectList()
                .flatMapIterable(events -> {
                    Mono<Product> productSaved = productRepository.saveAProduct(new Product(
                            command.getName(),
                            command.getDescription(),
                            0,
                            command.getPrice(),
                            command.getCategory(),
                            command.getBranchId()
                    ));

                    BranchAggregate branch = BranchAggregate.from(BranchId.of(command.getBranchId()),events);
                    branch.addProduct(
                            ProductId.of(productSaved.block().getId()),
                            new Name(command.getName()),
                            new Category(command.getCategory()),
                            new Description(command.getDescription()),
                            new Price(command.getPrice())
                    );
                    return branch.getUncommittedChanges();
                }).map(event -> {
                    eventBus.publish(event);
                    return event;
                }).flatMap(repository::saveEvent)
        );
    }

}
