package com.seulah.appdesign.apicontroller.nafith.repo;

import com.seulah.appdesign.apicontroller.nafith.model.NafithCreateSingleSanad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateSingleSanadGroupRepo extends MongoRepository<NafithCreateSingleSanad , String> {
}
