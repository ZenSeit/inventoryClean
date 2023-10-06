package co.diegofer.inventoryclean.usecase.storage.getinvoicesbybranchid;

import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
import co.diegofer.inventoryclean.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetInvoicesByBranchIdUseCase {

    private final InvoiceRepository invoiceRepository;

    public Flux<InvoiceData> apply(String branchId) {
        return invoiceRepository.findInvoicesByBranchId(branchId);
    }
}
