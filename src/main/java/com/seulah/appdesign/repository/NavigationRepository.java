package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Navigations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NavigationRepository extends MongoRepository<Navigations, String> {
}
