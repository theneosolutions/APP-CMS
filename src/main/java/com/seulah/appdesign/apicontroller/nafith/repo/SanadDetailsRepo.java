package com.seulah.appdesign.apicontroller.nafith.repo;

import com.seulah.appdesign.apicontroller.nafith.model.SanadDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanadDetailsRepo extends MongoRepository<SanadDetails,String> {
}
