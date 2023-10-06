package co.diegofer.inventoryclean.mongo;

import co.diegofer.inventoryclean.model.InvoiceEntity;
import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
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
public class MongoInvoiceAdapter implements InvoiceRepository{

    private final ReactiveMongoTemplate template;
    private final JSONMapper eventSerializer;


    public MongoInvoiceAdapter(ReactiveMongoTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }


    @Override
    public Mono<InvoiceEntity> saveInvoice(InvoiceEntity invoice) {
        return template.save(invoice);
    }

    @Override
    public Flux<InvoiceData> findInvoicesByBranchId(String branchId) {
        Query query = new Query(Criteria.where("branchId").is(branchId));
        return template.find(query, InvoiceData.class, "invoiceEntity");
    }

}
