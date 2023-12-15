package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Branding;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandingRepository extends MongoRepository<Branding, String> {
    Branding findByBrandName(String brandingName);
}
