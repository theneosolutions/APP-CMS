package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingScreen;
import com.seulah.appdesign.repository.BrandScreenRepository;
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
public class BrandScreenService {

    private final BrandScreenRepository brandScreenRepository;
    private final BrandingRepository brandingRepository;

    public BrandScreenService(BrandScreenRepository brandScreenRepository, BrandingRepository brandingRepository) {
        this.brandScreenRepository = brandScreenRepository;
        this.brandingRepository = brandingRepository;
    }


    public ResponseEntity<MessageResponse> saveBrandingScreen(List<String> screens, String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            BrandingScreen brandingScreen = new BrandingScreen();
            brandingScreen.setBrandId(brandId);
            brandingScreen.setScreens(screens);
            brandingScreen = brandScreenRepository.save(brandingScreen);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingScreen, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> getScreenByBrandId(String brandId) {
        Optional<BrandingScreen> brandingScreen = brandScreenRepository.findByBrandId(brandId);
        return brandingScreen.map(brand -> new ResponseEntity<>(new MessageResponse("Success", brand, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<BrandingScreen> brandingScreens = brandScreenRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", brandingScreens, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingScreen> brandingScreen = brandScreenRepository.findById(id);
        if (brandingScreen.isPresent()) {
            brandScreenRepository.delete(brandingScreen.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, List<String> brandScreens) {
        Optional<BrandingScreen> optionalBrandingScreen = brandScreenRepository.findById(id);
        if (optionalBrandingScreen.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
        }
        BrandingScreen brandingScreen = optionalBrandingScreen.get();
        if (brandScreens != null && !brandScreens.isEmpty()) {
            brandingScreen.setScreens(brandScreens);
        }

        brandingScreen = brandScreenRepository.save(brandingScreen);
        return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingScreen, false), HttpStatus.OK);
    }
}

