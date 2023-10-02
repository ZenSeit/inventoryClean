package co.diegofer.inventoryclean.events;


import co.diegofer.inventoryclean.events.data.ErrorEvent;
import co.diegofer.inventoryclean.events.data.Notification;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.usecase.generics.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqEventBus implements EventBus {

    public static final String EXCHANGE = "inventory_exchange";
    public static final String ROUTING_KEY = "inventory.events.routing.key";
    private final RabbitTemplate template;
    private final JSONMapper eventSerializer;

    public RabbitMqEventBus(RabbitTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public void publish(DomainEvent event) {
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
