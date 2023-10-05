package co.diegofer.inventoryclean.mongo;

import co.diegofer.inventoryclean.model.InvoiceEntity;
import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
import co.diegofer.inventoryclean.mongo.data.StoredEvent;
import co.diegofer.inventoryclean.serializer.JSONMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Repository
public class MongoInvoiceAdapter implements InvoiceRepository {

    private final ReactiveMongoTemplate template;
    private final JSONMapper eventSerializer;


    public MongoInvoiceAdapter(ReactiveMongoTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    public Flux<DomainEvent> findById(String aggregateId) {
        return template.find(new Query(Criteria.where("aggregateRootId").is(aggregateId)), StoredEvent.class)
                .sort(Comparator.comparing(StoredEvent::getOccurredOn))
                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer));
    }


    @Override
    public Mono<InvoiceEntity> saveInvoice(InvoiceEntity invoice) {
        return template.save(invoice);
    }

    /*public Mono<InvoiceEntity> saveInvoice(InvoiceEntity invoice) {
        *//*StoredEvent storedEvent = new StoredEvent();
        storedEvent.setAggregateRootId(event.aggregateRootId());
        storedEvent.setEventBody(StoredEvent.wrapEvent(event, eventSerializer));
        storedEvent.setOccurredOn(new Date());
        storedEvent.setTypeName(event.getClass().getTypeName());*//*
        return template.save(invoice);
    }*/
}
