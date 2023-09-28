package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.commands.RegisterBranchCommand;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.usecase.addstocktoproduct.AddStockToProductUseCase;
import co.diegofer.inventoryclean.usecase.getproductsbybranchid.GetProductsByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.reducestockproduct.ReduceStockProductUseCase;
import co.diegofer.inventoryclean.usecase.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.registeruser.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> saveBranch(Handler handler){
        return route(POST("/branches").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterBranchUseCase);
    }


    @Bean
    public RouterFunction<ServerResponse> saveProduct(Handler handler){
        return route(POST("/product").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTAddProduct);
    }

    @Bean
    public RouterFunction<ServerResponse> saveUser(RegisterUserUseCase registerUserUseCase){
        return route(POST("/users").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(product -> registerUserUseCase.apply(product)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> getProductsByBranch(GetProductsByBranchIdUseCase getProductsByBranchIdUseCase) {
        return route(GET("/products/{branch_id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProductsByBranchIdUseCase.apply(request.pathVariable("branch_id")), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> patchAddProductStock(AddStockToProductUseCase addStockToProductUseCase) {
        return route(PATCH("/products/id/{id}/stock/{stock}/add"),
                request -> addStockToProductUseCase.apply(request.pathVariable("id"), Integer.valueOf(request.pathVariable("stock")))
                        .flatMap(item -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> patchReduceProductStock(ReduceStockProductUseCase reduceStockProductUseCase) {
        return route(PATCH("/products/id/{id}/stock/{stock}/reduce"),
                request -> reduceStockProductUseCase.apply(request.pathVariable("id"), Integer.valueOf(request.pathVariable("stock")))
                        .flatMap(item -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}