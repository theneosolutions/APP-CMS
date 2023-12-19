package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.BrandingLogo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BrandLogoRepository extends MongoRepository<BrandingLogo, String> {

    List<BrandingLogo> findAllByBrandId(String brandId);

    BrandingLogo findByBrandId(String brandId);
}
