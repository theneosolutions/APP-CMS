package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BrandScreenRepository extends MongoRepository<BrandingScreen, String> {

    Optional<BrandingScreen> findByBrandId(String brandId);

    List<BrandingScreen> findAllByBrandId(String brandId);
}
