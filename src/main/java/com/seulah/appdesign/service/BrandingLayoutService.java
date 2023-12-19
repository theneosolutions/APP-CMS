package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.util.*;

@Service
@Slf4j
public class BrandingLayoutService {

    @Value("${application.bucket.name}")
    private String bucketName;
    private final BrandingLayoutRepository brandingLayoutRepository;

    private final BrandLogoService brandLogoService;

    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    public BrandingLayoutService(BrandingLayoutRepository brandingLayoutRepository, BrandLogoService brandLogoService, BrandingRepository brandingRepository, FileUploadService fileUploadService) {
        this.brandingLayoutRepository = brandingLayoutRepository;
        this.brandLogoService = brandLogoService;
        this.brandingRepository = brandingRepository;
        this.fileUploadService = fileUploadService;
    }

    public ResponseEntity<MessageResponse> createBrandingLayout(MultipartFile icon, String brandId, MultipartFile lottieFile) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            String fileExtension = brandLogoService.getFileExtension(icon);
            if (!fileExtension.equalsIgnoreCase("ico")) {
                log.error("Only ICO images are allowed.");
                return new ResponseEntity<>(new MessageResponse("Only ICO images are allowed. ", null, false), HttpStatus.BAD_REQUEST);
            }

            fileUploadService.uploadFile(icon);
            fileUploadService.uploadFile(lottieFile);
            saveToDatabase(icon, brandId, lottieFile);
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No record found against brand id", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String brandId, MultipartFile lottieFile) {
        BrandingLayout brandingLayout = new BrandingLayout();
        brandingLayout.setLottieFiles(lottieFile.getOriginalFilename());
        brandingLayout.setBrandId(brandId);
        brandingLayout.setIcon(file.getOriginalFilename());

        brandingLayoutRepository.save(brandingLayout);
        log.info("Branding logo saved to the database");
    }

    public ResponseEntity<MessageResponse> deleteBrandingLayout(String id) {
        Optional<BrandingLayout> optionalBrandingLayout = brandingLayoutRepository.findById(id);
        if (optionalBrandingLayout.isPresent()) {
            brandingLayoutRepository.delete(optionalBrandingLayout.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> getBrandingLayoutById(String id) {
        Optional<BrandingLayout> brandingLayout = brandingLayoutRepository.findById(id);
        return brandingLayout.map(layout -> new ResponseEntity<>(new MessageResponse("Success", layout, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }


    public String getLottieByBrandId(String brandId) {
        BrandingLayout brandingLayoutLottieFile = brandingLayoutRepository.findByBrandId(brandId);
        if (brandingLayoutLottieFile != null) {
            String fileName = brandingLayoutLottieFile.getLottieFiles();
            return fileUploadService.generateS3Url(bucketName, fileName);
        }
        return null;
    }

    public String getIconByBrandId(String brandId) {
        BrandingLayout brandingLayoutIcon = brandingLayoutRepository.findByBrandId(brandId);
        if (brandingLayoutIcon != null) {
            String fileName = brandingLayoutIcon.getIcon();
            return fileUploadService.generateS3Url(bucketName, fileName);
        }
        return null;
    }
}
