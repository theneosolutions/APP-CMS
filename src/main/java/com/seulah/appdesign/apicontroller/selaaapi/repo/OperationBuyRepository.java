package com.seulah.appdesign.apicontroller.selaaapi.repo;

import com.seulah.appdesign.apicontroller.selaaapi.dto.OperationsBuyResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationBuyRepository extends MongoRepository<OperationsBuyResponse,String> {
}
