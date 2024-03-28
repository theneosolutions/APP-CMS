package com.seulah.appdesign.apicontroller.nafith.repo;

import com.seulah.appdesign.apicontroller.nafith.model.CallBackDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallBackResponse extends MongoRepository<CallBackDetails,String> {
}
