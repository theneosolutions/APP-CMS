package com.seulah.appdesign.apicontroller.gosi.repo;

import com.seulah.appdesign.apicontroller.gosi.dto.Gosi;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Muhammad Mansoor
 */
public interface GosiRepo extends MongoRepository<Gosi,String> {
}
