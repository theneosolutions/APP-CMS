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
public class BrandFontService {

    private final BrandFontRepository brandFontRepository;
    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    public BrandFontService(BrandFontRepository brandFontRepository, BrandingRepository brandingRepository, FileUploadService fileUploadService) {
        this.brandFontRepository = brandFontRepository;
        this.brandingRepository = brandingRepository;
        this.fileUploadService = fileUploadService;
    }


    public ResponseEntity<MessageResponse> saveBrandingFont(MultipartFile fontFile, String brandId) {
        Optional<Branding> brandingOptional = brandingRepository.findById(brandId);
        if (brandingOptional.isPresent()) {
            fileUploadService.uploadFile(fontFile);
            saveToDatabase(fontFile, brandId);
            return new ResponseEntity<>(new MessageResponse("Successfully Uploaded", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String brandId) {
        BrandingFont brandingFont = brandFontRepository.findByBrandId(brandId).orElse(null);
        if (brandingFont == null) {
            brandingFont = new BrandingFont();
            brandingFont.setFont(file.getOriginalFilename());
        } else {
            brandingFont.setFont(file.getOriginalFilename());

        }
        brandingFont.setBrandId(brandId);
        brandFontRepository.save(brandingFont);
        log.info("Branding logo saved to the database");
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingFont> brandingFont = brandFontRepository.findById(id);
        if (brandingFont.isPresent()) {
            brandFontRepository.delete(brandingFont.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public byte[] getFontFileUrlByBrandId(String brandId) throws NoSuchFileException {
        Optional<BrandingFont> brandingFont = brandFontRepository.findByBrandId(brandId);
        if (brandingFont.isPresent()) {
            String fileName = brandingFont.get().getFont();
            return fileUploadService.downloadFile(fileName);
        }
        return null;
    }
}

