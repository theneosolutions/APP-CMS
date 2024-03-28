package com.seulah.appdesign.apicontroller.nafath.repo;

import com.seulah.appdesign.apicontroller.nafath.entity.NafathPayload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NafathPayloadRepo extends MongoRepository<NafathPayload,String> {
}
