package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.branch.Branch;
import co.diegofer.inventoryclean.model.branch.gateways.BranchRepository;
import co.diegofer.inventoryclean.r2dbc.data.BranchData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {

    private final ObjectMapper mapper;

    private final BranchRepositoryR2dbc branchRepository;

    private final DatabaseClient dbClient;

    @Override
    public Mono<Branch> saveABranch(Branch branch) {
        BranchData branchData = mapper.map(branch, BranchData.class);
        return branchRepository.saveBranch(branchData.getId(),branchData.getName(),branchData.getLocation()).flatMap(savedBranchData -> Mono.just(mapper.map(savedBranchData, Branch.class)))
                .onErrorMap(DataIntegrityViolationException.class, e -> new DataIntegrityViolationException("Error creating branch: "+e.getMessage()));


    }

    @Override
    public Flux<Branch> findAllBranches() {
        return branchRepository.findAll()
                .map(branch -> mapper.map(branch, Branch.class));
    }

    @Override
    public Mono<Branch> findBranchById(String branchId) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.empty())
                .map(branch -> mapper.map(branch, Branch.class))
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("Branch not found")));
    }

}
