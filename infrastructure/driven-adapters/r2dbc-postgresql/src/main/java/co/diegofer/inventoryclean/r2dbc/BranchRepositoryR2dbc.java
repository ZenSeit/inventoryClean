package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.r2dbc.data.BranchData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchRepositoryR2dbc extends ReactiveCrudRepository<BranchData, String> {
}
