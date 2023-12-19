package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface BrandColorRepository extends MongoRepository<BrandingColor, String> {

    @Query(value = "{}", fields = "{ 'colors' : 1}")
    List<Map<String, String>> findAllColors();

    Optional<BrandingColor> findByBrandId(String brandId);

    List<BrandingColor> findAllByBrandId(String brandId);
}
