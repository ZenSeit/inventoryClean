package co.diegofer.inventoryclean.api;


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
    public RouterFunction<ServerResponse> saveSuper(Handler handler){
        return route(POST("api/v1/user/registersuper").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterSuper);
    }


    @Bean
    public RouterFunction<ServerResponse> patchAddProductStock(Handler handler) {
        return route(PATCH("/api/v1/product/purchase").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHAddStock);
    }

    @Bean
    public RouterFunction<ServerResponse> patchRegisterFinalCustomerSale(Handler handler){
        return route(PATCH("/api/v1/product/customer-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterFinalCustomerSale);
    }

    @Bean
    public RouterFunction<ServerResponse> patchRegisterResellerSale(Handler handler){
        return route(PATCH("/api/v1/product/reseller-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterResellerSale);
    }

}
