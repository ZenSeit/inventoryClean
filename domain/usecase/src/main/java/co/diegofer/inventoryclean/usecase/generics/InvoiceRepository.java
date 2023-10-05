package co.diegofer.inventoryclean.usecase.generics;

import co.diegofer.inventoryclean.model.InvoiceEntity;
import reactor.core.publisher.Mono;

public interface InvoiceRepository {

    public Mono<InvoiceEntity> saveInvoice(InvoiceEntity invoice);
}
