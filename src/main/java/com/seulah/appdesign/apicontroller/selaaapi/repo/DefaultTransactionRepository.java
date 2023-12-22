package com.seulah.appdesign.apicontroller.selaaapi.repo;

import com.seulah.appdesign.apicontroller.selaaapi.dto.DefaultTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultTransactionRepository extends MongoRepository<DefaultTransaction,String> {
}
