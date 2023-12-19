package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

@Service
@Slf4j
public class BrandingLayoutService {


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
        brandingLayout.setBrandId(brandId);
        brandingLayout.setLottieFiles(lottieFile.getOriginalFilename());
        brandingLayout.setIcon(file.getOriginalFilename());
        brandingLayoutRepository.save(brandingLayout);

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


    public List<byte[]> getLottieByBrandId(String brandId) throws IOException {
        List<BrandingLayout> brandingLayoutLottieFile = brandingLayoutRepository.findAllByBrandId(brandId);
        List<byte[]> iconContents = new ArrayList<>();

        if (brandingLayoutLottieFile != null && !brandingLayoutLottieFile.isEmpty()) {
            for (BrandingLayout icon : brandingLayoutLottieFile) {
                byte[] content = fileUploadService.downloadFile(icon.getLottieFiles());
                iconContents.add(content);
            }
        }

        return iconContents;
    }

    public List<byte[]> getIconByBrandId(String brandId) throws IOException {
        List<BrandingLayout> brandingLayoutIcons = brandingLayoutRepository.findAllByBrandId(brandId);
        List<byte[]> iconContents = new ArrayList<>();

        if (brandingLayoutIcons != null && !brandingLayoutIcons.isEmpty()) {
            for (BrandingLayout icon : brandingLayoutIcons) {
                byte[] content = fileUploadService.downloadFile(icon.getIcon());
                iconContents.add(content);
            }
        }

        return iconContents;
    }


}
