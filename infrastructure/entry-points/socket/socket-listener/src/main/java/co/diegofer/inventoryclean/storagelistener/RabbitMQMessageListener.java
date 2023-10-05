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
    private final Logger logger = Logger.getLogger("StorageMessageListener");
    private final JSONMapper mapper = new JSONMapperImpl();

    @RabbitListener(queues = "inventory.events.branch.created.socket.queue")
    public void process(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        DomainEvent event = (DomainEvent) eventSerializer
                .readFromJson(notification.getBody(), Class.forName(notification.getType()));
        System.out.println("Sending branch created event to socket");
        socketController.sendBranchCreated("mainSpace", event);
    }

}
