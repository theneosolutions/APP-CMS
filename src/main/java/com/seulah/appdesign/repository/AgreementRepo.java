package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.AgreementForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepo extends MongoRepository<AgreementForm,String> {
    AgreementForm findByTitle(String title);
}
