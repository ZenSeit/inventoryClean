package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.commands.*;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.usecase.addstocktoproduct.AddStockToProductUseCase;
import co.diegofer.inventoryclean.usecase.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import co.diegofer.inventoryclean.usecase.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.registerresellersale.RegisterResellerSaleUseCase;
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

    private final AddStockToProductUseCase addStockToProductUseCase;

    private final RegisterResellerSaleUseCase registerResellerSaleUseCase;


    public Mono<ServerResponse> listenPOSTRegisterBranchUseCase(ServerRequest serverRequest) {

        return registerBranchUseCase.apply(serverRequest.bodyToMono(RegisterBranchCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    public Mono<ServerResponse> listenPOSTAddProduct(ServerRequest serverRequest) {

        return registerProductUseCase.apply(serverRequest.bodyToMono(AddProductCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });

    }

    public Mono<ServerResponse> listenPOSTRegisterUser(ServerRequest serverRequest) {

        return registerUserUseCase.apply(serverRequest.bodyToMono(RegisterUserCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });

    }

    public Mono<ServerResponse> listenPATCHRegisterFinalCustomerSale(ServerRequest serverRequest) {


        return registerFinalCustomerSaleUseCase.apply(serverRequest.bodyToMono(RegisterFinalCustomerSaleCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });

    }

    public Mono<ServerResponse> listenPATCHRegisterResellerSale (ServerRequest serverRequest) {


        return registerResellerSaleUseCase.apply(serverRequest.bodyToMono(RegisterResellerCustomerSaleCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });

    }

    public Mono<ServerResponse> listenPATCHAddStock(ServerRequest serverRequest) {


        return addStockToProductUseCase.apply(serverRequest.bodyToMono(BuyProductCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                }).next()
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
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
