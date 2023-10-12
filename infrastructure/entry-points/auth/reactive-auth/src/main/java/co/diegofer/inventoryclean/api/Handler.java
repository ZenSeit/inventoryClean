package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.commands.*;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.usecase.service.addstocktoproduct.AddStockToProductUseCase;
import co.diegofer.inventoryclean.usecase.service.loginuser.LoginUserUseCase;
import co.diegofer.inventoryclean.usecase.service.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.service.registerresellersale.RegisterResellerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registersuper.RegisterSuperUseCase;
import co.diegofer.inventoryclean.usecase.service.registeruser.RegisterUserUseCase;
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


    private final RegisterUserUseCase registerUserUseCase;

    private final RegisterFinalCustomerSaleUseCase registerFinalCustomerSaleUseCase;

    private final AddStockToProductUseCase addStockToProductUseCase;

    private final RegisterResellerSaleUseCase registerResellerSaleUseCase;

    private final RegisterSuperUseCase registerSuperUseCase;

    private final LoginUserUseCase loginUserUseCase;



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

    public Mono<ServerResponse> listenPOSTRegisterSuper(ServerRequest serverRequest) {

        return registerSuperUseCase.apply(serverRequest.bodyToMono(RegisterUserCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                })
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });

    }

    public Mono<ServerResponse> listenPOSTLoginUser(ServerRequest serverRequest) {

        return loginUserUseCase.apply(serverRequest.bodyToMono(LoginCommand.class))
                .flatMap(domainEvent -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(domainEvent));
                })
                .onErrorResume(Exception.class, e -> {
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

}
