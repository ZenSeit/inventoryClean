package co.diegofer.inventoryclean.usecase.generics;

import co.diegofer.inventoryclean.model.generic.Command;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class UserCaseForCommand <R extends Command> implements Function<Mono<R>, Flux<DomainEvent>> {

    public abstract Flux<DomainEvent> apply(Mono<R> rMono);
}