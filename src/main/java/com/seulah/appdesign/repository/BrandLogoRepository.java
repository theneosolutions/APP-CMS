package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingLogo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BrandLogoRepository extends MongoRepository<BrandingLogo, String> {

    Optional<BrandingLogo> findByBrandId(String brandId);

    List<BrandingLogo> findAllByBrandId(String brandId);
}
