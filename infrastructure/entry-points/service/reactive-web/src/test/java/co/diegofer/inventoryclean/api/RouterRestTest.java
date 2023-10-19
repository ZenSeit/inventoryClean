package co.diegofer.inventoryclean.api;

import co.diegofer.inventoryclean.model.commands.AddProductCommand;
import co.diegofer.inventoryclean.model.commands.BuyProductCommand;
import co.diegofer.inventoryclean.model.commands.RegisterBranchCommand;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.RegisterFinalCustomerSaleCommand;
import co.diegofer.inventoryclean.model.commands.RegisterUserCommand;
import co.diegofer.inventoryclean.model.commands.custom.LocationCommand;
import co.diegofer.inventoryclean.model.commands.custom.ProductToAdd;
import co.diegofer.inventoryclean.model.events.*;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.usecase.service.addstocktoproduct.AddStockToProductUseCase;
import co.diegofer.inventoryclean.usecase.service.loginuser.LoginUserUseCase;
import co.diegofer.inventoryclean.usecase.service.registerbranch.RegisterBranchUseCase;
import co.diegofer.inventoryclean.usecase.service.registerfinalcustomersale.RegisterFinalCustomerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registerproduct.RegisterProductUseCase;
import co.diegofer.inventoryclean.usecase.service.registerresellersale.RegisterResellerSaleUseCase;
import co.diegofer.inventoryclean.usecase.service.registersuper.RegisterSuperUseCase;
import co.diegofer.inventoryclean.usecase.service.registeruser.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private RegisterBranchUseCase registerBranchUseCase;

    @Mock
    private RegisterProductUseCase registerProductUseCase;

    @Mock
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private RegisterSuperUseCase registerSuperUseCase;

    @Mock
    private AddStockToProductUseCase addStockToProductUseCase;

    @Mock
    private RegisterFinalCustomerSaleUseCase registerFinalCustomerSaleUseCase;

    @Mock
    private RegisterResellerSaleUseCase registerResellerSaleUseCase;

    @BeforeEach
    void setUp() {
        Handler handler = new Handler(
               registerBranchUseCase,
                registerProductUseCase,
                registerUserUseCase,
                registerFinalCustomerSaleUseCase,
                addStockToProductUseCase,
                registerResellerSaleUseCase,
                registerSuperUseCase
        );
        webTestClient = WebTestClient
                .bindToRouterFunction(new RouterRest().saveBranch(handler)
                        .andOther(new RouterRest().saveProduct(handler))
                        .andOther(new RouterRest().saveUser(handler))
                        .andOther(new RouterRest().saveSuper(handler))
                        .andOther(new RouterRest().patchAddProductStock(handler))
                        .andOther(new RouterRest().patchRegisterFinalCustomerSale(handler))
                        .andOther(new RouterRest().patchRegisterResellerSale(handler)))
                .configureClient()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    void saveBranch() {

        RegisterBranchCommand registerBranchCommand = new RegisterBranchCommand(
                "name",
                new LocationCommand("Location","country")
        );

        Flux<DomainEvent> event = Flux.just(new BranchCreated("name", "Location, country"));

        when(registerBranchUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.post()
                .uri("api/v1/branch/register")
                .bodyValue(registerBranchCommand)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BranchCreated.class)
                .value(response -> {
                    assertEquals(registerBranchCommand.getName(), response.getName());
                });
    }

    @Test
    void saveProduct() {

        AddProductCommand addProductCommand = new AddProductCommand();
        addProductCommand.setName("product");
        addProductCommand.setPrice(1000);
        addProductCommand.setDescription("description");
        addProductCommand.setBranchId("BranchId");
        addProductCommand.setCategory("category");

        Flux<DomainEvent> event = Flux.just(new ProductAdded("productId", "product", "category", "description", 1000));

        when(registerProductUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.post()
                .uri("api/v1/product/register")
                .bodyValue(addProductCommand)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductAdded.class)
                .value(response -> {
                    assertEquals(addProductCommand.getName(), response.getName());
                });

    }

    @Test
    void saveUser() {

        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setName("name");
        registerUserCommand.setLastName("lastName");
        registerUserCommand.setEmail("email@email.com");
        registerUserCommand.setPassword("password");
        registerUserCommand.setRole("ADMIN");

        Flux<DomainEvent> event = Flux.just(new UserAdded("userId", "name", "lastName", "email", "password", "ADMIN"));

        when(registerUserUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.post()
                .uri("api/v1/user/register")
                .bodyValue(registerUserCommand)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserAdded.class)
                .value(response -> {
                    assertEquals(registerUserCommand.getName(), response.getName());
                });

    }

    @Test
    void saveSuper() {

        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setName("name");
        registerUserCommand.setLastName("lastName");
        registerUserCommand.setEmail("email@email.com");
        registerUserCommand.setPassword("password");
        registerUserCommand.setRole("SUPER");

        Mono<User> user = Mono.just(new User("name", "lastName", "email", "password", "SUPER", null));

        when(registerSuperUseCase.apply(any(Mono.class))).thenReturn(user);

        webTestClient.post()
                .uri("api/v1/user/registersuper")
                .bodyValue(registerUserCommand)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserAdded.class)
                .value(response -> {
                    assertEquals(registerUserCommand.getName(), response.getName());
                });
    }

    @Test
    void patchAddProductStock() {

        List<ProductToAdd> products = new ArrayList<>();
        products.add(new ProductToAdd("productID", 5));
        BuyProductCommand buyProductCommand = new BuyProductCommand();
        buyProductCommand.setBranchId("root");
        buyProductCommand.setProducts(products);

        Flux<DomainEvent> event = Flux.just(new StockAdded(products));

        when(addStockToProductUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.patch()
                .uri("/api/v1/product/purchase")
                .bodyValue(buyProductCommand)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void patchRegisterFinalCustomerSale() {

        List<ProductSale> productSaleList = new ArrayList<>();
        productSaleList.add(new ProductSale("productID", "product", 2));
        RegisterFinalCustomerSaleCommand registerFinalCustomerSaleCommand = new RegisterFinalCustomerSaleCommand();
        registerFinalCustomerSaleCommand.setBranchId("root");
        registerFinalCustomerSaleCommand.setProducts(productSaleList);

        Flux<DomainEvent> event = Flux.just(new FinalCustomerSaleRegistered(productSaleList, 2000));

        when(registerFinalCustomerSaleUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.patch()
                .uri("/api/v1/product/customer-sale")
                .bodyValue(registerFinalCustomerSaleCommand)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void patchRegisterResellerSale() {

        List<ProductSale> productSaleList = new ArrayList<>();
        productSaleList.add(new ProductSale("productID", "product", 2));
        RegisterFinalCustomerSaleCommand registerFinalCustomerSaleCommand = new RegisterFinalCustomerSaleCommand();
        registerFinalCustomerSaleCommand.setBranchId("root");
        registerFinalCustomerSaleCommand.setProducts(productSaleList);

        Flux<DomainEvent> event = Flux.just(new FinalCustomerSaleRegistered(productSaleList, 2000));

        when(registerResellerSaleUseCase.apply(any(Mono.class))).thenReturn(event);

        webTestClient.patch()
                .uri("/api/v1/product/reseller-sale")
                .bodyValue(registerFinalCustomerSaleCommand)
                .exchange()
                .expectStatus().isOk();
    }
}