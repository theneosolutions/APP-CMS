package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.apache.commons.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.*;

@Service
@Slf4j
public class BrandLogoService {

    private final BrandLogoRepository brandingLogoRepository;
    private final FileUploadService fileUploadService;

    private final BrandingRepository brandingRepository;

    public BrandLogoService(BrandLogoRepository brandingLogoRepository, FileUploadService fileUploadService, BrandingRepository brandingRepository) {
        this.brandingLogoRepository = brandingLogoRepository;
        this.fileUploadService = fileUploadService;
        this.brandingRepository = brandingRepository;
    }


    @Transactional
    public ResponseEntity<MessageResponse> saveBrandingLogo(MultipartFile file, String brandId, int height, int width) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No brand exist with this id", null, false), HttpStatus.BAD_REQUEST);
        }
        if (!getFileExtension(file).equalsIgnoreCase("png") && !getFileExtension(file).equalsIgnoreCase("svg")) {
            log.error("Only PNG and SVG images are allowed.");
            return new ResponseEntity<>(new MessageResponse("Only PNG and SVG images are allowed. ", null, false), HttpStatus.BAD_REQUEST);
        }
        BrandingLogo brandingLogo = brandingLogoRepository.findByBrandId(brandId);
        if (brandingLogo != null) {
            fileUploadService.deleteFile(brandingLogo.getLogo());
        }
        fileUploadService.uploadFile(file);
        saveToDatabase(file, brandId, height, width);
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return FilenameUtils.getExtension(originalFilename);
    }

    public byte[] getLogoFileUrlByBrandId(String brandId) throws NoSuchFileException {
        BrandingLogo brandingLogo = brandingLogoRepository.findByBrandId(brandId);
        if (brandingLogo != null) {
            String fileName = brandingLogo.getLogo();
            return fileUploadService.downloadFile(fileName);
        }
        return null;
    }


    private void saveToDatabase(MultipartFile file, String brandId, int height, int width) {
        BrandingLogo brandingLogo = brandingLogoRepository.findByBrandId(brandId);
        if (brandingLogo == null) {
            brandingLogo = new BrandingLogo();
            brandingLogo.setBrandId(brandId);
            brandingLogo.setHeight(height);
            brandingLogo.setWidth(width);
            brandingLogo.setLogo(file.getOriginalFilename());
        } else {
            brandingLogo.setLogo(file.getOriginalFilename());
            brandingLogo.setBrandId(brandId);
            brandingLogo.setWidth(width);
            brandingLogo.setHeight(height);
        }
        brandingLogoRepository.save(brandingLogo);
        log.info("Branding logo saved to the database");
    }
}

