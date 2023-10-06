package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.usecase.storage.getallbranches.GetAllBranchesUseCase;
import co.diegofer.inventoryclean.usecase.storage.getallproducts.GetAllProductsUseCase;
import co.diegofer.inventoryclean.usecase.storage.getbranchbyid.GetBranchByIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getinvoicesbybranchid.GetInvoicesByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getproductsbybranchid.GetProductsByBranchIdUseCase;
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
    public RouterFunction<ServerResponse> getProductsByBranch(GetProductsByBranchIdUseCase getProductsByBranchIdUseCase) {
        return route(GET("/api/products/{branch_id}"),
                request -> getProductsByBranchIdUseCase.apply(request.pathVariable("branch_id"))
                        .collectList()
                        .flatMap(product -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(product))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
                        )
        );

    }

    @Bean
    public RouterFunction<ServerResponse> getBranches(GetAllBranchesUseCase getAllBranchesUseCase) {
        return route(GET("/api/v1/branch"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBranchesUseCase.apply(), Branch.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> getBranchById(GetBranchByIdUseCase getBranchByIdUseCase) {
        return route(GET("/api/v1/branch/{branch_id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getBranchByIdUseCase.apply(request.pathVariable("branch_id")), Branch.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/api/v1/product"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.apply(), Product.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> getInvoicesByBranch(GetInvoicesByBranchIdUseCase getInvoicesByBranchIdUseCase) {
        return route(GET("/api/v1/invoice/{branch_id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getInvoicesByBranchIdUseCase.apply(request.pathVariable("branch_id")), InvoiceData.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );

    }


}
