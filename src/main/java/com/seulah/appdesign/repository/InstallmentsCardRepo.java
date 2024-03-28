package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.InstallmentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentsCardRepo extends MongoRepository<InstallmentsEntity,String> {
}
