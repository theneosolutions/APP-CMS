package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BannerRepository extends MongoRepository<Banner,String> {
}
