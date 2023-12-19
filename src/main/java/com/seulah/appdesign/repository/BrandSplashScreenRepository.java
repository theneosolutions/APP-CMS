package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingSplashScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BrandSplashScreenRepository extends MongoRepository<BrandingSplashScreen, String> {

    Optional<BrandingSplashScreen> findByBrandId(String brandId);

}
