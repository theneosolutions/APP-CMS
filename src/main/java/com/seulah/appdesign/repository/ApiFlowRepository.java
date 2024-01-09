package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.ApiFlow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiFlowRepository extends MongoRepository<ApiFlow,String> {

    ApiFlow findByBrandId(String brandId);
}
