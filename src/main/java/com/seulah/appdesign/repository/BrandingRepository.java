package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Branding;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BrandingRepository extends MongoRepository<Branding, String> {
    Branding findByBrandName(String brandingName);

    List<Branding> findByLanguageCode(String lowerCase);
}
