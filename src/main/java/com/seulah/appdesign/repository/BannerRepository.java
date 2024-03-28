package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BannerRepository extends MongoRepository<Banner,String> {
    List<Banner> findByLanguageCode(String langCode);
}
