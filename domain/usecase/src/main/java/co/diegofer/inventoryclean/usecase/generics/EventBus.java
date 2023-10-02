package co.diegofer.inventoryclean.usecase.generics;


import co.diegofer.inventoryclean.model.generic.DomainEvent;

public interface EventBus {

    void publish(DomainEvent event);
    void publishError(Throwable errorEvent);
}
