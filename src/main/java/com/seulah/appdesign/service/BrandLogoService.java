package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingLogo;
import com.seulah.appdesign.repository.BrandLogoRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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
            log.error("data not found against brand id {}", brandId);
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
        try {
            fileUploadService.uploadFile(file);
        } catch (Exception e) {
            log.error("Exception during uploading file ", e);
        }
        saveToDatabase(file, brandId, height, width);
        log.info("Brand logo saved successfully");
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return FilenameUtils.getExtension(originalFilename);
    }

    public byte[] getLogoFileUrlByBrandId(String brandId) {
        BrandingLogo brandingLogo = brandingLogoRepository.findByBrandId(brandId);
        if (brandingLogo != null) {
            String fileName = brandingLogo.getLogo();
            return fileUploadService.downloadFile(fileName);
        }
        return new byte[0];
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

