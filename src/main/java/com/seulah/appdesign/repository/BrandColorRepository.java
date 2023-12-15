package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingColor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BrandColorRepository extends MongoRepository<BrandingColor, String> {

    Optional<BrandingColor> findByBrandId(String brandId);
}
