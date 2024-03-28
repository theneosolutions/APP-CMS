package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.ScreenFlow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScreenFlowRepository extends MongoRepository<ScreenFlow,String> {
    ScreenFlow findByBrandId(String brandId);

}
