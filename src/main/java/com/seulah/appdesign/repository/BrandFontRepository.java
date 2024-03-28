package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface BrandFontRepository extends MongoRepository<BrandingFont, String> {


    Optional<BrandingFont> findByBrandId(String brandId);

    List<BrandingFont> findAllByBrandId(String brandId);


    BrandingFont findByName(String brandId);
}
