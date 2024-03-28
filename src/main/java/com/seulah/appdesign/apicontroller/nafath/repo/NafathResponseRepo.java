package com.seulah.appdesign.apicontroller.nafath.repo;

import com.seulah.appdesign.apicontroller.nafath.entity.NafathResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NafathResponseRepo extends MongoRepository<NafathResponse,String> {
}
