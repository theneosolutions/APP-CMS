package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Terms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepo extends MongoRepository<Terms,String> {
    Terms findByTitle(String title);
}
