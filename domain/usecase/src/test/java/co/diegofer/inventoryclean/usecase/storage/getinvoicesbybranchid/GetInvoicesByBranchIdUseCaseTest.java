package co.diegofer.inventoryclean.usecase.storage.getinvoicesbybranchid;

import co.diegofer.inventoryclean.model.commands.RegisterFinalCustomerSaleCommand.ProductSale;
import co.diegofer.inventoryclean.model.invoice.InvoiceData;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetInvoicesByBranchIdUseCaseTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    private GetInvoicesByBranchIdUseCase getInvoicesByBranchIdUseCase;

    @BeforeEach
    void setUp() {
        getInvoicesByBranchIdUseCase = new GetInvoicesByBranchIdUseCase(invoiceRepository);
    }

    @Test
    void apply() {
        List<ProductSale> products = new ArrayList<>();
        List<InvoiceData> invoices = List.of(new InvoiceData("id", products, 0, LocalDateTime.now(), "sellType", "branchId"),
                new InvoiceData("id2", products, 0, LocalDateTime.now(), "sellType", "branchId"));

        when(invoiceRepository.findInvoicesByBranchId("branchId")).thenReturn(Flux.fromIterable(invoices));

        Flux<InvoiceData> result = getInvoicesByBranchIdUseCase.apply("branchId");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

    }



}