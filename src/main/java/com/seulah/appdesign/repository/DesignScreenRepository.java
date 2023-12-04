package com.seulah.appdesign.repository;


import com.seulah.appdesign.entity.DesignScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DesignScreenRepository extends MongoRepository<DesignScreen, String> {
}
