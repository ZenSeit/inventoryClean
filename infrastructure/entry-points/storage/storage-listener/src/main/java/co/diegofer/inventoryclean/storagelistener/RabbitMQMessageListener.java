package co.diegofer.inventoryclean.storagelistener;


import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.storagelistener.data.Notification;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.usecase.storage.mysqlupdater.MySQLUpdaterUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class RabbitMQMessageListener {

    private final MySQLUpdaterUseCase useCase;

    private final JSONMapper eventSerializer;
    public static final String GENERAL_EVENTS_QUEUE = "inventory.events.queue";
    private final Logger logger = Logger.getLogger("StorageMessageListener");


    @RabbitListener(queues = GENERAL_EVENTS_QUEUE)
    public void process(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        DomainEvent event = (DomainEvent) eventSerializer
                .readFromJson(notification.getBody(), Class.forName(notification.getType()));
        useCase.accept(event);
    }

}
