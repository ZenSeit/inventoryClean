package co.diegofer.inventoryclean.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE = "inventory_exchange";

    public static final String BRANCH_CREATED_SOCKET_QUEUE = "inventory.events.branch.created.socket.queue";
    public static final String PRODUCT_ADDED_SOCKET_QUEUE = "inventory.events.product.added.socket.queue";
    public static final String USER_ADDED_SOCKET_QUEUE = "inventory.events.user.added.socket.queue";
    public static final String CUSTOMER_SALE_REGISTERED_SOCKET_QUEUE = "inventory.events.customer.sale.registered.socket.queue";
    public static final String RESELLER_SALE_REGISTERED_SOCKET_QUEUE = "inventory.events.reseller.sale.registered.socket.queue";
    public static final String STOCK_ADDED_SOCKET_QUEUE = "inventory.events.stock.added.socket.queue";
    public static final String GENERAL_EVENTS_QUEUE = "inventory.events.queue";

    public static final String BRANCH_CREATED_ROUTING_KEY = "inventory.events.branch.created.#";
    public static final String PRODUCT_ADDED_ROUTING_KEY = "inventory.events.product.added.#";
    public static final String USER_ADDED_ROUTING_KEY = "inventory.events.user.added.routing.#";
    public static final String CUSTOMER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.customer.sale.registered.#";
    public static final String RESELLER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.reseller.sale.registered.#";
    public static final String STOCK_ADDED_ROUTING_KEY = "inventory.events.stock.added.#";
    public static final String GENERAL_ROUTING_KEY = "inventory.events.#";


    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Queue branchCreatedSocketQueue() {
        return new Queue(BRANCH_CREATED_SOCKET_QUEUE);
    }


    @Bean
    public Queue productCreatedSocketQueue() {
        return new Queue(PRODUCT_ADDED_SOCKET_QUEUE);
    }


    @Bean
    public Queue userCreatedSocketQueue() {
        return new Queue(USER_ADDED_SOCKET_QUEUE);
    }


    @Bean
    public Queue customerSaleRegisteredSocketQueue() {
        return new Queue(CUSTOMER_SALE_REGISTERED_SOCKET_QUEUE);
    }


    @Bean
    public Queue resellerSaleRegisteredSocketQueue() {
        return new Queue(RESELLER_SALE_REGISTERED_SOCKET_QUEUE);
    }


    @Bean
    public Queue stockAddedSocketQueue() {
        return new Queue(STOCK_ADDED_SOCKET_QUEUE);
    }


    @Bean
    public Queue generalQueue() {
        return new Queue(GENERAL_EVENTS_QUEUE);
    }


    @Bean
    public Binding branchCreatedSocketBinding() {
        return BindingBuilder
                .bind(this.branchCreatedSocketQueue())
                .to(this.eventsExchange())
                .with(BRANCH_CREATED_ROUTING_KEY);
    }


    @Bean
    public Binding productAddedSocketBinding() {
        return BindingBuilder
                .bind(this.productCreatedSocketQueue())
                .to(this.eventsExchange())
                .with(PRODUCT_ADDED_ROUTING_KEY);
    }


    @Bean
    public Binding userAddedSocketBinding() {
        return BindingBuilder
                .bind(this.userCreatedSocketQueue())
                .to(this.eventsExchange())
                .with(USER_ADDED_ROUTING_KEY);
    }


    @Bean
    public Binding customerSaleRegisteredSocketBinding() {
        return BindingBuilder
                .bind(this.customerSaleRegisteredSocketQueue())
                .to(this.eventsExchange())
                .with(CUSTOMER_SALE_REGISTERED_ROUTING_KEY);
    }


    @Bean
    public Binding resellerSaleRegisteredSocketBinding() {
        return BindingBuilder
                .bind(this.resellerSaleRegisteredSocketQueue())
                .to(this.eventsExchange())
                .with(RESELLER_SALE_REGISTERED_ROUTING_KEY);
    }

    @Bean
    public Binding stockAddedSocketBinding() {
        return BindingBuilder
                .bind(this.stockAddedSocketQueue())
                .to(this.eventsExchange())
                .with(STOCK_ADDED_ROUTING_KEY);
    }


    @Bean
    public Binding generalBinding() {
        return BindingBuilder
                .bind(this.generalQueue())
                .to(this.eventsExchange())
                .with(GENERAL_ROUTING_KEY);
    }
}
