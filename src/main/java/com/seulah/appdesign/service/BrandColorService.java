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
public class BrandColorService {

    private final BrandColorRepository brandColorRepository;
    private final BrandingRepository brandingRepository;

    public BrandColorService(BrandColorRepository brandColorRepository, BrandingRepository brandingRepository) {
        this.brandColorRepository = brandColorRepository;
        this.brandingRepository = brandingRepository;
    }


    public ResponseEntity<MessageResponse> saveBrandingColor(List<Map<String, String>> colors, String brandId) {
        Optional<Branding> brandingOptional = brandingRepository.findById(brandId);
        if (brandingOptional.isPresent()) {
            BrandingColor brandingColor = brandColorRepository.findByBrandId(brandId)
                    .orElseGet(BrandingColor::new);

            brandingColor.setBrandId(brandId);
            if (brandingColor.getColors() != null && !brandingColor.getColors().isEmpty()) {
                updateExistingColors(brandingColor.getColors(), colors);
            } else {
                brandingColor.setColors(colors);
            }
            brandingColor = brandColorRepository.save(brandingColor);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingColor, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    private void updateExistingColors(List<Map<String, String>> existingColors, List<Map<String, String>> newColors) {
        for (Map<String, String> newColor : newColors) {
            String key = newColor.get("key");
            String value = newColor.get("value");

            Optional<Map<String, String>> existingColor = existingColors.stream()
                    .filter(color -> color.get("key").equals(key))
                    .findFirst();
            if (existingColor.isPresent()) {
                existingColor.get().put("value", value);
            } else {
                existingColors.add(newColor);
            }

        }
    }

    public ResponseEntity<MessageResponse> getColorByBrandId(String brandId) {
        List<BrandingColor> brandingColors = brandColorRepository.findAllByBrandId(brandId);
        return new ResponseEntity<>(new MessageResponse("Success", brandingColors, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingColor> brandingScreen = brandColorRepository.findById(id);
        if (brandingScreen.isPresent()) {
            brandColorRepository.delete(brandingScreen.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

}

