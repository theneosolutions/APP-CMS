package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Navigations;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NavigationRepository extends MongoRepository<Navigations, String> {
    List<Navigations> findByLanguageCode(String lowerCase);
}
