package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.usecase.getallbranches.GetAllBranchesUseCase;
import co.diegofer.inventoryclean.usecase.getallproducts.GetAllProductsUseCase;
import co.diegofer.inventoryclean.usecase.getproductsbybranchid.GetProductsByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.service.reducestockproduct.ReduceStockProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> saveBranch(Handler handler){
        return route(POST("api/v1/branch/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterBranchUseCase);
    }


    @Bean
    public RouterFunction<ServerResponse> saveProduct(Handler handler){
        return route(POST("api/v1/product/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTAddProduct);
    }

    @Bean
    public RouterFunction<ServerResponse> saveUser(Handler handler){
        return route(POST("api/v1/user/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterUser);
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
    public RouterFunction<ServerResponse> getBranches(GetAllBranchesUseCase getAllBranchesUseCase) {
        return route(GET("/api/v1/branch/"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBranchesUseCase.apply(), Branch.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/api/v1/product/"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.apply(), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> patchAddProductStock(Handler handler) {
        return route(PATCH("/api/v1/product/purchase").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHAddStock);
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

    @Bean
    public RouterFunction<ServerResponse> patchRegisterFinalCustomerSale(Handler handler){
        return route(PATCH("/api/v1/product/customer-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterFinalCustomerSale);
    }

    @Bean
    public RouterFunction<ServerResponse> patchRegisterResellerSale(Handler handler){
        return route(PATCH("/api/v1/product/reseller-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterFinalCustomerSale);
    }

}