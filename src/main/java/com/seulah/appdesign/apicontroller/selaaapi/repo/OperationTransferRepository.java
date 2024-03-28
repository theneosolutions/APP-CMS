package com.seulah.appdesign.apicontroller.selaaapi.repo;

import com.seulah.appdesign.apicontroller.selaaapi.dto.OperationsTransferResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTransferRepository extends MongoRepository<OperationsTransferResponse, String> {
}
