package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

/**
 * @author Muhammad Mansoor
 */
public interface BrandingLayoutIconRepository extends MongoRepository<BrandingLayoutIcon, String> {
    List<BrandingLayoutIcon> findAllByBrandId(String brandId);
}
