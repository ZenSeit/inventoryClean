package co.diegofer.inventoryclean.usecase.storage;

import co.diegofer.inventoryclean.model.InvoiceEntity;
import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.model.events.*;
import co.diegofer.inventoryclean.model.generic.DomainUpdater;
import co.diegofer.inventoryclean.model.invoice.InvoiceRepository;
import co.diegofer.inventoryclean.model.product.Product;
import co.diegofer.inventoryclean.model.product.gateways.ProductRepository;
import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.model.values.invoice.InvoiceId;

import java.util.UUID;

public class MySQLUpdater extends DomainUpdater {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    //private final ViewBus bus;

    public MySQLUpdater(BranchRepository branchRepository, ProductRepository productRepository, UserRepository userRepository, InvoiceRepository invoiceRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;

        //this.bus = bus;
        //this.bus = bus;


        listen((BranchCreated event) -> {
            Branch branch = new Branch(event.aggregateRootId(), event.getName(), event.getLocation());
            branchRepository.saveABranch(branch).subscribe();
        });

        listen((ProductAdded event) -> {
            Product product = new Product(event.getProductId(), event.getName(),event.getDescription(), 0, event.getPrice(), event.getCategory(), event.aggregateRootId());
            productRepository.saveAProduct(product).subscribe();
        });

        listen((UserAdded event) -> {
            System.out.println(event.getUserId());
            User user = new User(event.getUserId(), event.getName(), event.getLastName(), event.getEmail(), event.getPassword(),event.getRole(), event.aggregateRootId());
            userRepository.saveAUser(user).subscribe();
        });

        listen((StockAdded event) -> {
            productRepository.addStock(event.getProducts()).subscribe();
        });

        listen((FinalCustomerSaleRegistered event) -> {
            productRepository.reduceStock(event.getProducts()).subscribe();
            invoiceRepository.saveInvoice(new InvoiceEntity(InvoiceId.of(UUID.randomUUID().toString()), event.getProducts(), event.getTotal(),event.getSellType() ,event.aggregateRootId())).subscribe();
        });

        listen((ResellerCustomerSaleRegistered event) -> {
            productRepository.reduceStock(event.getProducts()).subscribe();
            invoiceRepository.saveInvoice(new InvoiceEntity(InvoiceId.of(UUID.randomUUID().toString()), event.getProducts(), event.getTotal(),event.getSellType() ,event.aggregateRootId())).subscribe();
        });



    }

}
