package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.CompanyBrand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyBrandRepository extends MongoRepository<CompanyBrand,String> {
    Optional<CompanyBrand> findByBrandId(String brandId);
}
