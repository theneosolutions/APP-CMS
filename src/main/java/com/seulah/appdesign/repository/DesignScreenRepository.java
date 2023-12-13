package com.seulah.appdesign.repository;


import com.seulah.appdesign.entity.DesignComponent;
import com.seulah.appdesign.entity.DesignScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DesignScreenRepository extends MongoRepository<DesignScreen, String> {
    List<DesignScreen> findByDesignComponentListContaining(DesignComponent designComponent);
}
