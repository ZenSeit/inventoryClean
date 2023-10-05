package co.diegofer.inventoryclean.usecase.storage.mysqlupdater;


import co.diegofer.inventoryclean.model.generic.DomainEvent;
import co.diegofer.inventoryclean.usecase.storage.MySQLUpdater;

import java.util.function.Consumer;

public class MySQLUpdaterUseCase implements Consumer<DomainEvent> {

    private final MySQLUpdater updater;

    public MySQLUpdaterUseCase(MySQLUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void accept(DomainEvent domainEvent) {
        updater.applyEvent(domainEvent);
    }
}
