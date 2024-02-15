package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingSplashScreen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandSplashScreenRepository extends MongoRepository<BrandingSplashScreen, String> {

    Optional<BrandingSplashScreen> findByBrandId(String brandId);

}
