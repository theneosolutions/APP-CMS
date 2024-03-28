package com.seulah.appdesign.apicontroller.yakeen.rowad;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonInfoRepo extends MongoRepository<RowadPersonInfo,String> {
}
