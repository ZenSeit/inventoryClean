package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.commands.AddProductCommand;
import co.diegofer.inventoryclean.model.commands.RegisterBranchCommand;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.usecase.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import co.diegofer.inventoryclean.usecase.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final RegisterBranchUseCase registerBranchUseCase;

    private final RegisterProductUseCase registerProductUseCase;

    private final RegisterUserUseCase registerUserUseCase;

    private final RegisterFinalCustomerSaleUseCase registerFinalCustomerSaleUseCase;


    public Mono<ServerResponse> listenPOSTRegisterBranchUseCase(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(registerBranchUseCase
                                .apply(serverRequest.bodyToMono(RegisterBranchCommand.class)),
                        DomainEvent.class)).onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(throwable.getMessage()));
    }

    public Mono<ServerResponse> listenPOSTAddProduct(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(registerProductUseCase
                                .apply(serverRequest.bodyToMono(AddProductCommand.class)),
                        DomainEvent.class)).onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(throwable.getMessage()));
    }

    public Mono<ServerResponse> listenPOSTRegisterUser(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(registerUserUseCase
                                .apply(serverRequest.bodyToMono(RegisterUserCommand.class)),
                        DomainEvent.class)).onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> listenPATCHRegisterFinalCustomerSale(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(registerFinalCustomerSaleUseCase
                                .apply(serverRequest.bodyToMono(RegisterFinalCustomerSaleCommand.class)),
                        DomainEvent.class)).onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
