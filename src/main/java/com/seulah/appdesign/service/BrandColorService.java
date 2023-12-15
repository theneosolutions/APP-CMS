package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingColor;
import com.seulah.appdesign.repository.BrandColorRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public ResponseEntity<MessageResponse> saveBrandingColor(List<String> colors, String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            BrandingColor brandingColor = new BrandingColor();
            brandingColor.setBrandId(brandId);
            brandingColor.setColors(colors);
            brandingColor = brandColorRepository.save(brandingColor);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingColor, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> getColorByBrandId(String brandId) {
        Optional<BrandingColor> brandingScreen = brandColorRepository.findByBrandId(brandId);
        return brandingScreen.map(brand -> new ResponseEntity<>(new MessageResponse("Success", brand, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<BrandingColor> brandingColors = brandColorRepository.findAll();
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

    public ResponseEntity<MessageResponse> updateById(String id, List<String> colors) {
        Optional<BrandingColor> optionalBrandingScreen = brandColorRepository.findById(id);
        if (optionalBrandingScreen.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
        }
        BrandingColor brandingColor = optionalBrandingScreen.get();
        if (colors != null && !colors.isEmpty()) {
            brandingColor.setColors(colors);
        }

        brandingColor = brandColorRepository.save(brandingColor);
        return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingColor, false), HttpStatus.OK);
    }
}

