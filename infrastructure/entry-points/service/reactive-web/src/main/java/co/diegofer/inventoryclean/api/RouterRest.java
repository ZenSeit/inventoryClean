package co.diegofer.inventoryclean.api;


import co.diegofer.inventoryclean.api.config.BranchCommandSw;
import co.diegofer.inventoryclean.model.commands.*;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.events.*;
import co.diegofer.inventoryclean.usecase.service.addstocktoproduct.AddStockToProductUseCase;
import co.diegofer.inventoryclean.usecase.service.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.service.registerresellersale.RegisterResellerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registeruser.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperation(path = "api/v1/branch/register", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            //beanClass = RegisterBranchUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveBranch", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BranchCreated.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BranchCommandSw.class)))))
    public RouterFunction<ServerResponse> saveBranch(Handler handler){
        return route(POST("api/v1/branch/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterBranchUseCase);
    }


    @Bean
    @RouterOperation(path = "api/v1/product/register", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterProductUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveProduct", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ProductAdded.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AddProductCommand.class)))))
    public RouterFunction<ServerResponse> saveProduct(Handler handler){
        return route(POST("api/v1/product/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTAddProduct);
    }

    @Bean
    @RouterOperation(path = "api/v1/user/register", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterUserUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveUser", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserAdded.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RegisterUserCommand.class)))))
    public RouterFunction<ServerResponse> saveUser(Handler handler){
        return route(POST("api/v1/user/register").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterUser);
    }

    @Bean
    @RouterOperation(path = "api/v1/user/registersuper", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterUserUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveUser", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserAdded.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RegisterUserCommand.class)))))
    public RouterFunction<ServerResponse> saveSuper(Handler handler){
        return route(POST("api/v1/user/registersuper").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTRegisterSuper);
    }


    @Bean
    @RouterOperation(path = "/api/v1/product/purchase", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = AddStockToProductUseCase.class, method = RequestMethod.PATCH, beanMethod = "apply",
            operation = @Operation(operationId = "patchAddProductStock", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Accepted", content = @Content(schema = @Schema(implementation = StockAdded.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BuyProductCommand.class)))))
    public RouterFunction<ServerResponse> patchAddProductStock(Handler handler) {
        return route(PATCH("/api/v1/product/purchase").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHAddStock);
    }

    @Bean
    @RouterOperation(path = "/api/v1/product/customer-sale", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterFinalCustomerSaleUseCase.class, method = RequestMethod.PATCH, beanMethod = "apply",
            operation = @Operation(operationId = "patchRegisterFinalCustomerSale", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Accepted", content = @Content(schema = @Schema(implementation = FinalCustomerSaleRegistered.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RegisterFinalCustomerSaleCommand.class)))))
    public RouterFunction<ServerResponse> patchRegisterFinalCustomerSale(Handler handler){
        return route(PATCH("/api/v1/product/customer-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterFinalCustomerSale);
    }

    @Bean
    @RouterOperation(path = "/api/v1/product/reseller-sale", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterResellerSaleUseCase.class, method = RequestMethod.PATCH, beanMethod = "apply",
            operation = @Operation(operationId = "patchRegisterResellerCustomerSale", tags = "Command usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Accepted", content = @Content(schema = @Schema(implementation = ResellerCustomerSaleRegistered.class))),
                            @ApiResponse(responseCode = "400")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RegisterResellerCustomerSaleCommand.class)))))
    public RouterFunction<ServerResponse> patchRegisterResellerSale(Handler handler){
        return route(PATCH("/api/v1/product/reseller-sale").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPATCHRegisterResellerSale);
    }

}
