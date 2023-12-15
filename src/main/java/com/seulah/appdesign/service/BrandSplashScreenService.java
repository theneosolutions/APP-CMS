package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingSplashScreen;
import com.seulah.appdesign.repository.BrandSplashScreenRepository;
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
public class BrandSplashScreenService {

    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandingRepository brandingRepository;

    public BrandSplashScreenService(BrandSplashScreenRepository brandSplashScreenRepository, BrandingRepository brandingRepository) {
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandingRepository = brandingRepository;
    }


    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(String splashScreen, String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            BrandingSplashScreen brandingSplashScreen = new BrandingSplashScreen();
            brandingSplashScreen.setBrandId(brandId);
            brandingSplashScreen.setSplashScreen(splashScreen);
            brandingSplashScreen = brandSplashScreenRepository.save(brandingSplashScreen);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingSplashScreen, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> getSplashScreenByBrandId(String brandId) {
        Optional<BrandingSplashScreen> brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId);
        return brandingSplashScreen.map(splashScreen -> new ResponseEntity<>(new MessageResponse("Success", splashScreen, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<BrandingSplashScreen> brandingSplashScreens = brandSplashScreenRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", brandingSplashScreens, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingSplashScreen> brandingSplashScreenOptional = brandSplashScreenRepository.findById(id);
        if (brandingSplashScreenOptional.isPresent()) {
            brandSplashScreenRepository.delete(brandingSplashScreenOptional.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, String splashScreen) {
        Optional<BrandingSplashScreen> brandingSplashScreenOptional = brandSplashScreenRepository.findById(id);
        if (brandingSplashScreenOptional.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
        }
        BrandingSplashScreen brandingSplashScreen = brandingSplashScreenOptional.get();
        if (splashScreen != null && !splashScreen.isEmpty()) {
            brandingSplashScreen.setSplashScreen(splashScreen);
        }

        brandingSplashScreen = brandSplashScreenRepository.save(brandingSplashScreen);
        return new ResponseEntity<>(new MessageResponse("Successfully Updated", brandingSplashScreen, false), HttpStatus.OK);
    }
}

