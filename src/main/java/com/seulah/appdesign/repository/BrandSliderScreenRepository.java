package com.seulah.appdesign.repository;


import com.seulah.appdesign.entity.BrandSliderScreen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandSliderScreenRepository extends MongoRepository<BrandSliderScreen, String> {

    @Query("{ 'brandId' : ?0 }")
    List<BrandSliderScreen> findByBrandId(String brandId);
    Optional<BrandSliderScreen> findByMainTittle(String mainTittle);
}
