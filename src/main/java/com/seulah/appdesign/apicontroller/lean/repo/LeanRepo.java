package com.seulah.appdesign.apicontroller.lean.repo;

import com.seulah.appdesign.apicontroller.lean.model.LeanEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeanRepo extends MongoRepository<LeanEntity,String> {
}
