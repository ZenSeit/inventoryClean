package co.diegofer.inventoryclean.viewbus;


import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.usecase.generics.ViewBus;
import co.diegofer.inventoryclean.viewbus.data.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQViewBus implements ViewBus {

    public static final String EXCHANGE = "inventory_exchange";
    public static final String BRANCH_CREATED_ROUTING_KEY = "inventory.events.branch.created.#";
    public static final String PRODUCT_ADDED_ROUTING_KEY = "inventory.events.product.added.#";
    public static final String USER_ADDED_ROUTING_KEY = "inventory.events.user.added.#";
    public static final String CUSTOMER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.customer.sale.registered.#";
    public static final String RESELLER_SALE_REGISTERED_ROUTING_KEY = "inventory.events.reseller.sale.registered.#";
    public static final String STOCK_ADDED_ROUTING_KEY = "inventory.events.stock.added.#";
    private final RabbitTemplate template;
    private final JSONMapper eventSerializer;

    public RabbitMQViewBus(RabbitTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }


    @Override
    public void publishBranch(DomainEvent event) {
        template.convertAndSend(
                EXCHANGE,
                BRANCH_CREATED_ROUTING_KEY,
                new Notification(
                        event.getClass().getTypeName(),
                        eventSerializer.writeToJson(event)
                )
                        .serialize()
                        .getBytes()
        );
    }

    @Override
    public void publishUser(User user) {

    }

    @Override
    public void publishProduct(Product product) {

    }
}
