package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingColor;
import com.seulah.appdesign.entity.BrandingSplashScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BrandSplashScreenRepository extends MongoRepository<BrandingSplashScreen, String> {

    Optional<BrandingSplashScreen> findByBrandId(String brandId);

}
