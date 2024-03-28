package com.seulah.appdesign.apicontroller.gosi.repo;

import com.seulah.appdesign.apicontroller.gosi.dto.Gosi;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author Muhammad Mansoor
 */
public interface GosiRepo extends MongoRepository<Gosi,String> {
    Optional<Gosi> findById(String id);
}
