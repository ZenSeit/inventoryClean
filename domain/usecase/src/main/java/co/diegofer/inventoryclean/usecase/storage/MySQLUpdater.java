package co.diegofer.inventoryclean.usecase.storage;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.events.BranchCreated;
import co.diegofer.inventoryclean.model.events.ProductAdded;
import co.diegofer.inventoryclean.model.generic.DomainUpdater;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.usecase.generics.ViewBus;

public class MySQLUpdater extends DomainUpdater {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final ViewBus bus;

    public MySQLUpdater(BranchRepository branchRepository, ProductRepository productRepository, InvoiceRepository invoiceRepository, ViewBus bus) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.bus = bus;
        //this.bus = bus;


        listen((BranchCreated event) -> {
            Branch branch = new Branch(event.aggregateRootId(), event.getName(), event.getLocation());
            branchRepository.saveABranch(branch).subscribe();
            bus.publishBranch(event);
        });

        listen((ProductAdded event) -> {
            Product product = new Product(event.getProductId(), event.getName(),event.getDescription(), 0, event.getPrice(), event.getCategory(), event.aggregateRootId());
            //productRepository.saveAProduct(product).subscribe();
            bus.publishProduct(product);
        });

    }

}
