package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.*;

@Service
@Slf4j
public class BrandSplashScreenService {

    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    public BrandSplashScreenService(BrandSplashScreenRepository brandSplashScreenRepository, BrandingRepository brandingRepository, FileUploadService fileUploadService) {
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandingRepository = brandingRepository;
        this.fileUploadService = fileUploadService;
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingSplashScreen> brandingSplashScreenOptional = brandSplashScreenRepository.findById(id);
        if (brandingSplashScreenOptional.isPresent()) {
            brandSplashScreenRepository.delete(brandingSplashScreenOptional.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(MultipartFile splashScreenImage, String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            fileUploadService.uploadFile(splashScreenImage);
            saveToDatabase(splashScreenImage, brandId);
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String brandId) {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen == null) {
            brandingSplashScreen = new BrandingSplashScreen();
            brandingSplashScreen.setSplashScreen(file.getOriginalFilename());
            brandingSplashScreen.setBrandId(brandId);

        } else {
            brandingSplashScreen.setSplashScreen(file.getOriginalFilename());
            brandingSplashScreen.setBrandId(brandId);
        }
        brandSplashScreenRepository.save(brandingSplashScreen);
        log.info("Branding logo saved to the database");
    }

    public byte[] getBrandSplashScreenByBrandId(String brandId) throws NoSuchFileException {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen != null) {
            String fileName = brandingSplashScreen.getSplashScreen();
            return fileUploadService.downloadFile(fileName);
        }
        return null;
    }
}
