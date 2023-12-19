package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface BrandingLayoutRepository extends MongoRepository<BrandingLayout, String> {
    BrandingLayout findByBrandId(String id);

    List<BrandingLayout> findAllByBrandId(String brandId);

    BrandingLayout findByLottieFiles(String lottieFiles);

    BrandingLayout findByIcon(String lottieFiles);
}
