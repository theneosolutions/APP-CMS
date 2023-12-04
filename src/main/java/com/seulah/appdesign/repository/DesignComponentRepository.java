package com.seulah.appdesign.repository;


import com.seulah.appdesign.entity.DesignComponent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DesignComponentRepository extends MongoRepository<DesignComponent, String> {
}
