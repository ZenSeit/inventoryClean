package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.r2dbc.data.BranchData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BranchRepositoryR2dbc extends ReactiveCrudRepository<BranchData, String> {

    @Query("insert into Branch(id, name, location) values(:id, :name, :location)")
    Mono<BranchData> saveBranch(@Param("id") String id,
                                @Param("name") String name,
                                @Param("location") String location);

}
