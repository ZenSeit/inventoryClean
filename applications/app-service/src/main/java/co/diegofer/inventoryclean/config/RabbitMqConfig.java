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
    public static final String BRANCH_CREATED_QUEUE = "inventory.events.branch.created.queue";
    public static final String PRODUCT_ADDED_QUEUE = "inventory.events.product.added.queue";
    public static final String USER_ADDED_QUEUE = "inventory.events.user.added.queue";
    public static final String GENERAL_QUEUE = "inventory.events.queue";
    public static final String BRANCH_CREATED_ROUTING_KEY = "inventory.events.branch.created.routing.key";
    public static final String PRODUCT_ADDED_ROUTING_KEY = "inventory.events.product.added.routing.key";
    public static final String USER_ADDED_ROUTING_KEY = "inventory.events.user.added.routing.key";
    public static final String GENERAL_ROUTING_KEY = "inventory.events.routing.key";


    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue branchCreatedQueue() {
        return new Queue(BRANCH_CREATED_QUEUE);
    }

    @Bean
    public Queue productCreatedQueue() {
        return new Queue(PRODUCT_ADDED_QUEUE);
    }

    @Bean
    public Queue userCreatedQueue() {
        return new Queue(USER_ADDED_QUEUE);
    }


    @Bean
    public Queue generalQueue() {
        return new Queue(GENERAL_QUEUE);
    }


    @Bean
    public Binding branchCreatedBinding() {
        return BindingBuilder
                .bind(this.branchCreatedQueue())
                .to(this.eventsExchange())
                .with(BRANCH_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding productAddedBinding() {
        return BindingBuilder
                .bind(this.productCreatedQueue())
                .to(this.eventsExchange())
                .with(PRODUCT_ADDED_ROUTING_KEY);
    }

    @Bean
    public Binding userAddedBinding() {
        return BindingBuilder
                .bind(this.userCreatedQueue())
                .to(this.eventsExchange())
                .with(USER_ADDED_ROUTING_KEY);
    }


    @Bean
    public Binding generalBinding() {
        return BindingBuilder
                .bind(this.generalQueue())
                .to(this.eventsExchange())
                .with(GENERAL_ROUTING_KEY);
    }
}
