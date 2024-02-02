package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.InstallmentsEntity;
import com.seulah.appdesign.repository.InstallmentsCardRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InstallmentCardService {

    private final InstallmentsCardRepo installmentsCardRepo;

    public InstallmentCardService(InstallmentsCardRepo installmentsCardRepo) {
        this.installmentsCardRepo = installmentsCardRepo;
    }

    public ResponseEntity<?> getInstallments(){
        return ResponseEntity.ok().body(installmentsCardRepo.findAll());
    }
    public ResponseEntity<?> addInstallments(InstallmentsEntity installmentsEntity){
        return ResponseEntity.ok().body(installmentsCardRepo.save(installmentsEntity));
    }
}
