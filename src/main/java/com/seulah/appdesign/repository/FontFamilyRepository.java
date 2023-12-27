package com.seulah.appdesign.repository;

import com.seulah.appdesign.entity.FontFamily;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Muhammad Mansoor
 */
public interface FontFamilyRepository extends MongoRepository<FontFamily, String> {
}
