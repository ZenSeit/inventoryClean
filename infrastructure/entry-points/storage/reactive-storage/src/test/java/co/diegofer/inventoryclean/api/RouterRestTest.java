package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.usecase.storage.getallbranches.GetAllBranchesUseCase;
import co.diegofer.inventoryclean.usecase.storage.getallproducts.GetAllProductsUseCase;
import co.diegofer.inventoryclean.usecase.storage.getbranchbyid.GetBranchByIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getinvoicesbybranchid.GetInvoicesByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getproductsbybranchid.GetProductsByBranchIdUseCase;
import co.diegofer.inventoryclean.usecase.storage.getusersbybranch.GetUsersByBranchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private GetProductsByBranchIdUseCase getProductsByBranchIdUseCase;

    @Mock
    private GetAllBranchesUseCase getBranchesUseCase;

    @Mock
    private GetBranchByIdUseCase getBranchByIdUseCase;

    @Mock
    private GetAllProductsUseCase getAllProductsUseCase;

    @Mock
    private GetInvoicesByBranchIdUseCase getInvoicesByBranchIdUseCase;

    @Mock
    private GetUsersByBranchUseCase getUsersByBranchUseCase;


    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(new RouterRest().getProductsByBranch(getProductsByBranchIdUseCase)
                        .andOther(new RouterRest().getBranches(getBranchesUseCase)).andOther(new RouterRest().getBranchById(getBranchByIdUseCase))
                        .andOther(new RouterRest().getInvoicesByBranch(getInvoicesByBranchIdUseCase))
                        .andOther(new RouterRest().getUserByBranch(getUsersByBranchUseCase)))
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();
    }




    @Test
    void getProductsByBranch() {

        List<Product> products = List.of(new Product("productId","name","description",0,1000,"general","branchId"),
                new Product("productId2","name2","description",0,1000,"general","branchId"));

        when(getProductsByBranchIdUseCase.apply(anyString()))
                .thenReturn(Flux.fromIterable(products));

        // Envía una solicitud GET al endpoint y verifica la respuesta
        webTestClient.get()
                .uri("/api/products/branchId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Product.class) // Product.class representa la clase de tu objeto de producto
                .hasSize(2);
    }


    @Test
    void getBranches() {

        List<Branch> branches = List.of(new Branch("branchId","name","location"),new Branch("branchId2","name","location"));

        when(getBranchesUseCase.apply())
                .thenReturn(Flux.fromIterable(branches));

        // Envía una solicitud GET al endpoint y verifica la respuesta
        webTestClient.get()
                .uri("/api/v1/branch")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Branch.class) // Product.class representa la clase de tu objeto de producto
                .hasSize(2);

    }


    @Test
    void getBranchById() {

        Branch branch = new Branch("branchId","name","location");

        when(getBranchByIdUseCase.apply(anyString()))
                .thenReturn(Mono.just(branch));

        // Envía una solicitud GET al endpoint y verifica la respuesta
        webTestClient.get()
                .uri("/api/v1/branch/branchId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Branch.class) // Product.class representa la clase de tu objeto de producto
                .hasSize(1);

    }


    /*@Test
    void getAllProducts() {

        List<Product> sampleProducts = List.of(
                new Product("product1", "name1", 0, 1000, "General", "branch1"),
                new Product("product2", "name2", 0, 1000, "General", "branch2")
        );

            when(getAllProductsUseCase.apply()).thenReturn(Flux.fromIterable(sampleProducts));

            webTestClient.get()
                    .uri("/api/v1/product")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(Product.class)
                    .hasSize(2);
    }*/

    @Test
    void getInvoicesByBranch() {

        List<ProductSale> products = new ArrayList<>();
        List<InvoiceData> invoices = List.of(new InvoiceData("id", products, 0, LocalDateTime.now(), "sellType", "branchId"),
                new InvoiceData("id2", products, 0, LocalDateTime.now(), "sellType", "branchId"));

        when(getInvoicesByBranchIdUseCase.apply(anyString())).thenReturn(Flux.fromIterable(invoices));

        webTestClient.get()
                .uri("/api/v1/invoice/branchId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(InvoiceData.class)
                .hasSize(2);
    }

    @Test
    void getUserByBranch() {

        List<User> users = List.of(new User("userId","name","lastName","email","password","branchId"),
                new User("userId2","name2","lastName2","email2","password2","branchId"));

        when(getUsersByBranchUseCase.apply(anyString())).thenReturn(Flux.fromIterable(users));

        webTestClient.get()
                .uri("/api/v1/users/branchId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class)
                .hasSize(2);

    }
}