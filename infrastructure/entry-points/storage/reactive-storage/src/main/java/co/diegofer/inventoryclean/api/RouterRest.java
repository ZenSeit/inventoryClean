package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.usecase.storage.getallbranches.GetAllBranchesUseCase;
import co.diegofer.inventoryclean.usecase.storage.getallproducts.GetAllProductsUseCase;
import co.diegofer.inventoryclean.usecase.storage.getbranchbyid.GetBranchByIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getinvoicesbybranchid.GetInvoicesByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getproductsbybranchid.GetProductsByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getusersbybranch.GetUsersByBranchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @RouterOperation(path = "/api/products/{branch_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            //beanClass = GetProductsByBranchIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getProductsByBranch", tags = "Storage usecases",
                    parameters = {
                            @Parameter(name = "branch_id", description = "Branch ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
                            @ApiResponse(responseCode = "400", description = "Branch not found")
                    }))
    public RouterFunction<ServerResponse> getProductsByBranch(GetProductsByBranchIdUseCase getProductsByBranchIdUseCase) {
        return route(GET("/api/products/{branch_id}"),
                request -> getProductsByBranchIdUseCase.apply(request.pathVariable("branch_id"))
                        .collectList()
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromPublisher(getProductsByBranchIdUseCase.apply(request.pathVariable("branch_id")),
                                        Product.class))
                        ).onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage())));

    }

    @Bean
    @RouterOperation(path = "/api/v1/branch", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllBranchesUseCase.class, method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "viewAllBranches", tags = "Storage usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Branch.class))))
                    }))
    public RouterFunction<ServerResponse> getBranches(GetAllBranchesUseCase getAllBranchesUseCase) {
        return route(GET("/api/v1/branch"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBranchesUseCase.apply(), Branch.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    @RouterOperation(path = "/api/v1/branch/{branch_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetBranchByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getBranchById", tags = "Storage usecases",
                    parameters = {
                            @Parameter(name = "branch_id", description = "Branch ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (array = @ArraySchema(schema = @Schema(implementation = Branch.class)))),
                            @ApiResponse(responseCode = "400", description = "Branch not found")
                    }))
    public RouterFunction<ServerResponse> getBranchById(GetBranchByIdUseCase getBranchByIdUseCase) {
        return route(GET("/api/v1/branch/{branch_id}"),
                request -> getBranchByIdUseCase.apply(request.pathVariable("branch_id"))
                        .flatMap(branch -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromPublisher(getBranchByIdUseCase.apply(request.pathVariable("branch_id")),
                                        Branch.class))
                        ).onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage())));

    }

    /*@Bean
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/api/v1/product"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.apply(), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }*/

    @Bean
    @RouterOperation(path = "/api/v1/invoice/{branch_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetInvoicesByBranchIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getInvoicesByBranch", tags = "Storage usecases",
                    parameters = {
                            @Parameter(name = "branch_id", description = "Branch ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = InvoiceData.class)))),
                            @ApiResponse(responseCode = "400", description = "Branch not found")
                    }))
    public RouterFunction<ServerResponse> getInvoicesByBranch(GetInvoicesByBranchIdUseCase getInvoicesByBranchIdUseCase) {
        return route(GET("/api/v1/invoice/{branch_id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getInvoicesByBranchIdUseCase.apply(request.pathVariable("branch_id")), InvoiceData.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    @RouterOperation(path = "/users/{branch_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetUsersByBranchUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getUsersByBranch", tags = "Storage usecases",
                    parameters = {
                            @Parameter(name = "branch_id", description = "Branch ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
                            @ApiResponse(responseCode = "400", description = "Branch not found")
                    }))
    public RouterFunction<ServerResponse> getUserByBranch(GetUsersByBranchUseCase getUsersByBranchUseCase) {
        return route(GET("/api/v1/users/{branch_id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUsersByBranchUseCase.apply(request.pathVariable("branch_id")), User.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }


}
