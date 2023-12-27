package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingColor;
import com.seulah.appdesign.repository.BrandColorRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            log.info("Successfully updated brand color {}", colors);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingColor, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    private void updateExistingColors(List<Map<String, String>> existingColors, List<Map<String, String>> newColors) {
        List<Map<String, String>> colorsToAdd = new ArrayList<>();
        for (Map<String, String> newColor : newColors) {
            String key = newColor.get("name");
            String value = newColor.get("color");

            Optional<Map<String, String>> existingColor = existingColors.stream()
                    .filter(color -> {
                        String colorKey = color.get("name");
                        return key != null && key.equals(colorKey);
                    })
                    .findFirst();

            if (existingColor.isPresent()) {
                existingColor.get().put("color", value);
            } else {
                colorsToAdd.add(newColor);
            }
        }
        existingColors.addAll(colorsToAdd);
    }


    public ResponseEntity<MessageResponse> getColorByBrandId(String brandId) {
        List<BrandingColor> brandingColors = brandColorRepository.findAllByBrandId(brandId);
        return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, brandingColors, false), HttpStatus.OK);
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

