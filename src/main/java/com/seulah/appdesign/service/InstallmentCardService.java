package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.InstallmentsEntity;
import com.seulah.appdesign.repository.InstallmentsCardRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class InstallmentCardService {

    private final InstallmentsCardRepo installmentsCardRepo;
    private final  FileUploadService uploadService;
    private String s3Url = "https://seulahbucket.s3.amazonaws.com/";
    public InstallmentCardService(InstallmentsCardRepo installmentsCardRepo, FileUploadService uploadService) {
        this.installmentsCardRepo = installmentsCardRepo;
        this.uploadService = uploadService;
    }

    public ResponseEntity<?> getInstallments(){
        List<InstallmentsEntity> installmentsEntityList = installmentsCardRepo.findAll();
        return ResponseEntity.ok().body(installmentsEntityList);
    }
    public ResponseEntity<?> addInstallments(InstallmentsEntity installmentsEntity, MultipartFile file){
        if(uploadService.uploadFile(file)!=null) {
            installmentsEntity.setUrl(s3Url + file.getOriginalFilename());
            return ResponseEntity.ok().body(installmentsCardRepo.save(installmentsEntity));
        }else {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_GATEWAY);
        }
    }
}
