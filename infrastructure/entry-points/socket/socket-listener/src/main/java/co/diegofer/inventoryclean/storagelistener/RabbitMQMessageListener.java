package co.diegofer.inventoryclean.storagelistener;



import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.serializer.JSONMapperImpl;
import co.diegofer.inventoryclean.socketresponder.SocketController;
import co.diegofer.inventoryclean.storagelistener.data.Notification;
import lombok.AllArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class RabbitMQMessageListener {

    private final SocketController socketController;

    private final JSONMapper eventSerializer;

    public static final String BRANCH_CREATED_SOCKET_QUEUE = "inventory.events.branch.created.socket.queue";
    public static final String PRODUCT_ADDED_SOCKET_QUEUE = "inventory.events.product.added.socket.queue";
    public static final String USER_ADDED_SOCKET_QUEUE = "inventory.events.user.added.socket.queue";
    public static final String CUSTOMER_SALE_REGISTERED_SOCKET_QUEUE = "inventory.events.customer.sale.registered.socket.queue";
    public static final String RESELLER_SALE_REGISTERED_SOCKET_QUEUE = "inventory.events.reseller.sale.registered.socket.queue";
    public static final String STOCK_ADDED_SOCKET_QUEUE = "inventory.events.stock.added.socket.queue";
    private final Logger logger = Logger.getLogger("StorageMessageListener");
    private final JSONMapper mapper = new JSONMapperImpl();

    @RabbitListener(queues = BRANCH_CREATED_SOCKET_QUEUE)
    public void process(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        DomainEvent event = (DomainEvent) eventSerializer
                .readFromJson(notification.getBody(), Class.forName(notification.getType()));
        System.out.println("Sending branch created event to socket");
        socketController.sendBranchCreated("mainSpace", event);
    }

    @RabbitListener(queues = {PRODUCT_ADDED_SOCKET_QUEUE,STOCK_ADDED_SOCKET_QUEUE,CUSTOMER_SALE_REGISTERED_SOCKET_QUEUE,RESELLER_SALE_REGISTERED_SOCKET_QUEUE})
    public void processTwo(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        DomainEvent event = (DomainEvent) eventSerializer
                .readFromJson(notification.getBody(), Class.forName(notification.getType()));
        System.out.println("Sending product created event to socket");
        socketController.sendProductAdded(event.aggregateRootId(), event);
    }

}
