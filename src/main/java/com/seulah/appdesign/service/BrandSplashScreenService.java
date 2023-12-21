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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class BrandSplashScreenService {

    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    public BrandSplashScreenService(BrandSplashScreenRepository brandSplashScreenRepository, BrandingRepository brandingRepository, FileUploadService fileUploadService) {
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandingRepository = brandingRepository;
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


    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(MultipartFile splashScreenImage,
                                                                    MultipartFile splashScreen1,
                                                                    MultipartFile splashScreen2,
                                                                    MultipartFile splashScreen3, String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isPresent()) {
            // fileUploadService.uploadFile(splashScreenImage);
            try {
                // Get the content of the file as a byte array
                byte[] fileBytes = splashScreenImage.getBytes();
                byte[] fileBytes1 = splashScreen1.getBytes();
                byte[] fileBytes2 = splashScreen2.getBytes();
                byte[] fileBytes3 = splashScreen3.getBytes();
                // Convert the byte array to a String (you can modify this based on your use case)
                String fileContent = new String(fileBytes);
                String fileContent1 = new String(fileBytes1);
                String fileContent2 = new String(fileBytes2);
                String fileContent3 = new String(fileBytes3);
                saveToDatabase(fileContent, fileContent1, fileContent2, fileContent3, brandId);


                return new ResponseEntity<>(new MessageResponse("Record has been saved", null, false), HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.NOT_FOUND);
    }

    private void saveToDatabase(String file, String file1, String file2, String file3, String brandId) {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen == null) {
            brandingSplashScreen = new BrandingSplashScreen();

        }
        brandingSplashScreen.setSplashScreen(file);
        brandingSplashScreen.setSplashScreen1(file1);
        brandingSplashScreen.setSplashScreen2(file2);
        brandingSplashScreen.setSplashScreen3(file3);
        brandingSplashScreen.setBrandId(brandId);
        brandSplashScreenRepository.save(brandingSplashScreen);
        log.info("Branding logo saved to the database");
    }

    public String getBrandSplashScreenByBrandId(String brandId) {
        BrandingSplashScreen brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId).orElse(null);
        if (brandingSplashScreen != null) {
            return brandingSplashScreen.getSplashScreen();
        }
        return null;
    }
}
