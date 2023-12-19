package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Slf4j
public class BrandFontService {

    private final BrandFontRepository brandFontRepository;
    private final BrandingRepository brandingRepository;

    public BrandFontService(BrandFontRepository brandFontRepository, BrandingRepository brandingRepository) {
        this.brandFontRepository = brandFontRepository;
        this.brandingRepository = brandingRepository;
    }


    public ResponseEntity<MessageResponse> saveBrandingFont(List<Map<String, String>> fonts, String brandId) {
        Optional<Branding> brandingOptional = brandingRepository.findById(brandId);
        if (brandingOptional.isPresent()) {
            BrandingFont brandingFont = brandFontRepository.findByBrandId(brandId)
                    .orElseGet(BrandingFont::new);

            brandingFont.setBrandId(brandId);
//            if (brandingFont.getFonts() != null && !brandingFont.getFonts().isEmpty()) {
//                updateExistingFonts(brandingFont.getFonts(), fonts);
//            } else {
            brandingFont.setFonts(fonts);

            brandingFont = brandFontRepository.save(brandingFont);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingFont, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getFontByBrandId(String brandId) {
        List<BrandingFont> brandingFonts = brandFontRepository.findAllByBrandId(brandId);
        return new ResponseEntity<>(new MessageResponse("Success", brandingFonts, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingFont> brandingFont = brandFontRepository.findById(id);
        if (brandingFont.isPresent()) {
            brandFontRepository.delete(brandingFont.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


}

