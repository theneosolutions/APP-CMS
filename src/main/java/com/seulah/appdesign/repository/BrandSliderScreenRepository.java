package com.seulah.appdesign.repository;


import com.seulah.appdesign.entity.BrandSliderScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BrandSliderScreenRepository extends MongoRepository<BrandSliderScreen, String> {

    Optional<BrandSliderScreen> findByBrandId(String brandId);

}
