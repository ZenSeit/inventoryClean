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
    @Override
    public Mono<User> saveAUser(User user) {
        String newId = UUID.randomUUID().toString();
        UserData userData = mapper.map(user, UserData.class);
        dbClient.sql("INSERT INTO User(id, name, lastname, password, email, role, branch_id) VALUES(:id, :name, :lastname, :password, :email, :role, :branchId)")
                .bind("id", newId)
                .bind("name", userData.getName())
                .bind("lastname", userData.getLastName())
                .bind("password", userData.getPassword())
                .bind("email", userData.getEmail())
                .bind("role", userData.getRole())
                .bind("branchId", userData.getBranchId())
                .fetch()
                .one()
                .subscribe();
        user.setId(newId);
        return Mono.just(user);
    }
}
