package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.r2dbc.data.UserData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepositoryR2dbc extends ReactiveCrudRepository<UserData, String> {
}
