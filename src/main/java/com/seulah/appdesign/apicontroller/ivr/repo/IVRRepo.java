package com.seulah.appdesign.apicontroller.ivr.repo;


import com.seulah.appdesign.apicontroller.ivr.model.IVRStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVRRepo extends MongoRepository<IVRStatus,String> {
}
