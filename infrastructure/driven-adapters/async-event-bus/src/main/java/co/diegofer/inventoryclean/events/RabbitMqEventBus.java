package co.diegofer.inventoryclean.events;


import co.diegofer.inventoryclean.events.data.ErrorEvent;
import co.diegofer.inventoryclean.events.data.Notification;
import co.diegofer.inventoryclean.model.events.*;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqEventBus implements EventBus {

    public static final String EXCHANGE = "inventory_exchange";
    public static final String ROUTING_KEY = "inventory.events.routing.key";

    public static final String BRANCH_CREATED_ROUTING_KEY = "inventory.events.branch.created.routing.key";
    public static final String PRODUCT_ADDED_ROUTING_KEY = "inventory.events.product.added.routing.key";
    public static final String USER_ADDED_ROUTING_KEY = "inventory.events.user.added.routing.key";
    public static final String CUSTOMER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.customer.sale.registered.routing.key";
    public static final String RESELLER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.reseller.sale.registered.routing.key";
    public static final String STOCK_ADDED_ROUTING_KEY = "inventory.events.stock.added.routing.key";
    private final RabbitTemplate template;
    private final JSONMapper eventSerializer;

    public RabbitMqEventBus(RabbitTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public void publish(DomainEvent event) {
        String routingKey;
        if (event instanceof BranchCreated) routingKey = BRANCH_CREATED_ROUTING_KEY;
        else if (event instanceof ProductAdded) routingKey = PRODUCT_ADDED_ROUTING_KEY;
        else if (event instanceof UserAdded) routingKey = USER_ADDED_ROUTING_KEY;
        else if (event instanceof FinalCustomerSaleRegistered) routingKey = CUSTOMER_SALE_REGISTERED_ROUTING_KEY;
        else if (event instanceof ResellerCustomerSaleRegistered) routingKey = RESELLER_SALE_REGISTERED_ROUTING_KEY;
        else if (event instanceof StockAdded) routingKey = STOCK_ADDED_ROUTING_KEY;
        else routingKey = ROUTING_KEY;
        template.convertAndSend(
                EXCHANGE,
                routingKey,
                new Notification(
                        event.getClass().getTypeName(),
                        eventSerializer.writeToJson(event)
                )
                        .serialize()
                        .getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        ErrorEvent event = new ErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
        template.convertAndSend(
                EXCHANGE,
                ROUTING_KEY,
                new Notification(
                        event.getClass().getTypeName(),
                        eventSerializer.writeToJson(event)
                )
                        .serialize()
                        .getBytes()
        );
    }
}
