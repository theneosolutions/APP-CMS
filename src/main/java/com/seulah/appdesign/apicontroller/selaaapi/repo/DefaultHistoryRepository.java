package com.seulah.appdesign.apicontroller.selaaapi.repo;

import com.seulah.appdesign.apicontroller.selaaapi.dto.DefaultHistoryMain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultHistoryRepository extends MongoRepository<DefaultHistoryMain,String> {
}
