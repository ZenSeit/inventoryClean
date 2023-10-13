package co.diegofer.inventoryclean.usecase.storage.getusersbybranch;

import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;

public class GetUsersByBranchUseCase {

    private final UserRepository userRepository;

    public GetUsersByBranchUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Flux<User> apply(String branchId) {
        return userRepository.getUsersByBranch(branchId);
    }
}
