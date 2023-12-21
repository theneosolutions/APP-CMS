package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.BrandSliderScreen;
import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingSplashScreen;
import com.seulah.appdesign.repository.BrandSliderScreenRepository;
import com.seulah.appdesign.repository.BrandSplashScreenRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.BrandSliderRequest;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandSplashScreenService {

    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandingRepository brandingRepository;
    private final BrandSliderScreenRepository brandSliderScreenRepository;
    private final FileUploadService fileUploadService;

    public BrandSplashScreenService(BrandSplashScreenRepository brandSplashScreenRepository, BrandingRepository brandingRepository, BrandSliderScreenRepository brandSliderScreenRepository, FileUploadService fileUploadService) {
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandingRepository = brandingRepository;
        this.brandSliderScreenRepository = brandSliderScreenRepository;
        this.fileUploadService = fileUploadService;
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingSplashScreen> brandingSplashScreenOptional = brandSplashScreenRepository.findById(id);
        if (brandingSplashScreenOptional.isPresent()) {
            fileUploadService.deleteFile(brandingSplashScreenOptional.get().getSplashScreen());
            brandSplashScreenRepository.delete(brandingSplashScreenOptional.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(MultipartFile splashScreenImage,String brandId){

        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            // fileUploadService.uploadFile(splashScreenImage);
            try {
                // Get the content of the file as a byte array
                byte[] fileBytes = splashScreenImage.getBytes();
                // Convert the byte array to a String (you can modify this based on your use case)
                String fileContent = new String(fileBytes);

                saveToDatabase(fileContent, brandId);


                return new ResponseEntity<>(new MessageResponse("Record has been saved", null, false), HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.NOT_FOUND);
    }


    private void saveToDatabase(String file, String brandId) {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen == null) {
            brandingSplashScreen = new BrandingSplashScreen();
            brandingSplashScreen.setSplashScreen(file);
            brandingSplashScreen.setBrandId(brandId);

        } else {
            brandingSplashScreen.setSplashScreen(file);
            brandingSplashScreen.setBrandId(brandId);

        }
        brandingSplashScreen.setSplashScreen(file);
        brandingSplashScreen.setBrandId(brandId);
        brandSplashScreenRepository.save(brandingSplashScreen);
        log.info("Branding logo saved to the database");
    }


    public ResponseEntity<MessageResponse> saveBrandingSlidercreen(BrandSliderScreen brandSliderScreen) {
        Optional<BrandSliderScreen> branding = brandSliderScreenRepository.findByBrandId(brandSliderScreen.getBrandId());
        if (branding.isPresent()) {
            // fileUploadService.uploadFile(splashScreenImage);
            try {
                sliderSaveToDatabase(brandSliderScreen);
                return new ResponseEntity<>(new MessageResponse("Record has been saved", null, false), HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.NOT_FOUND);
    }

    private void sliderSaveToDatabase(BrandSliderScreen brandSliderScreenList) {
        brandSliderScreenRepository.save(brandSliderScreenList);
        log.info("Slider has been saved to the database");
    }
    public String getBrandSplashScreenByBrandId(String brandId) {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen != null) {
            return brandingSplashScreen.getSplashScreen();
        }
        return null;
    }
}
