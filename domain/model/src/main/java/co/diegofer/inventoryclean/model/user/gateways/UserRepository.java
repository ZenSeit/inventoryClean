package co.diegofer.inventoryclean.model.user.gateways;

import co.diegofer.inventoryclean.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    public Mono<User> saveAUser(User user);
}
