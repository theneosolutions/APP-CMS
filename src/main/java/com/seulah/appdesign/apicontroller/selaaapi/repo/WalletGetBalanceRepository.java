package com.seulah.appdesign.apicontroller.selaaapi.repo;

import com.seulah.appdesign.apicontroller.selaaapi.dto.WalletBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletGetBalanceRepository extends MongoRepository<WalletBalance, String> {
}
