package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.r2dbc.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final ObjectMapper mapper;

    private final DatabaseClient dbClient;

    private final UserRepositoryR2dbc userRepositoryR2dbc;

    @Override
    public Mono<User> saveAUser(User user) {
        String newId = UUID.randomUUID().toString();
        UserData userData = mapper.map(user, UserData.class);
        userData.setId(newId);

        return userRepositoryR2dbc.saveUser(userData.getId(),userData.getName(),userData.getLastName(),
                userData.getPassword(),userData.getEmail(),userData.getRole(),userData.getBranchId()).thenReturn(
                            mapper.map(userData, User.class)
                        );


    }
}
