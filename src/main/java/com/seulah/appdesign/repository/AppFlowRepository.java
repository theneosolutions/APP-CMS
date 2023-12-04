package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.AppFlow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppFlowRepository extends MongoRepository<AppFlow, String> {
}
