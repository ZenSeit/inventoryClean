package co.diegofer.inventoryclean.model.user.gateways;

import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    public Mono<User> saveAUser(User user);

    public Mono<User> saveASuper(User user);

    public Mono<AuthResponse> authenticate(AuthRequest authRequest);

    public Flux<User> getUsersByBranch(String branchId);
}
