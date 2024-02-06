package com.seulah.appdesign.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seulah.appdesign.entity.BrandSliderScreen;
import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingSplashScreen;
import com.seulah.appdesign.repository.BrandSliderScreenRepository;
import com.seulah.appdesign.repository.BrandSplashScreenRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.BrandSliderRequest;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandSplashScreenService {

    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandingRepository brandingRepository;
    private final BrandSliderScreenRepository brandSliderScreenRepository;
    private final FileUploadService fileUploadService;
    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;
    private String s3Url = "https://seulahbucket.s3.amazonaws.com/";
    private List<BrandSliderRequest> brandSliderRequests = new ArrayList<>();

    public BrandSplashScreenService(BrandSplashScreenRepository brandSplashScreenRepository, BrandingRepository brandingRepository, BrandSliderScreenRepository brandSliderScreenRepository, FileUploadService fileUploadService, AmazonS3 s3Client) {
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandingRepository = brandingRepository;
        this.brandSliderScreenRepository = brandSliderScreenRepository;
        this.fileUploadService = fileUploadService;
        this.s3Client = s3Client;
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


    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(MultipartFile splashScreenImage, String brandId) {

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


    public ResponseEntity<MessageResponse> saveBrandingSliderScreen(BrandSliderScreen brandSliderScreen, String pos) {
        BrandSliderScreen branding = brandSliderScreenRepository.findByMainTittle(brandSliderScreen.getMainTittle());
        if (branding != null) {
            // fileUploadService.uploadFile(splashScreenImage);
            try {
                for (BrandSliderRequest items : branding.getBrandSliderScreenList()) {
                    if (items.getPosition().equals(pos)) {
                        return new ResponseEntity<>(new MessageResponse("Position is already exist", null, false), HttpStatus.OK);
                    }
                }
                branding.getBrandSliderScreenList().addAll(brandSliderScreen.getBrandSliderScreenList());
                sliderSaveToDatabase(branding);
                return new ResponseEntity<>(new MessageResponse("Record has been updated", null, false), HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            sliderSaveToDatabase(brandSliderScreen);
            return new ResponseEntity<>(new MessageResponse("New Record has been saved", null, false), HttpStatus.OK);

        }
        return new ResponseEntity<>(new MessageResponse("Something Went Wrong", null, false), HttpStatus.BAD_GATEWAY);

    }

    private void sliderSaveToDatabase(BrandSliderScreen brandSliderScreen) {
        brandSliderScreenRepository.save(brandSliderScreen);
        log.info("Slider has been saved to the database");
    }

    public BrandingSplashScreen getBrandSplashScreenByBrandId(String brandId) {
        Optional<BrandingSplashScreen> brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId);
        try {
            return brandingSplashScreen.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<BrandSliderScreen> getBrandSliderScreenByBrandId(String brandId) {
        return brandSliderScreenRepository.findByBrandId(brandId);
    }

    BrandSliderScreen brandSliderScreen = new BrandSliderScreen();
    BrandSliderRequest brandSliderRequest;

    public ResponseEntity<?> saveAllSliders(String mainTittle, String brandId, String desc, String title,
                                            String position, MultipartFile file) {
        BrandSliderScreen items = brandSliderScreenRepository.findByMainTittle(mainTittle);
        if (items != null) {
            brandSliderScreen.setId(items.getId());
            brandSliderScreen.setMainTittle(items.getMainTittle());
            brandSliderScreen.setBrandId(items.getBrandId());
            items.getBrandSliderScreenList().add(new BrandSliderRequest(title, desc, s3Url + file.getOriginalFilename(), position)); // Add the new instance
            brandSliderScreen.setBrandSliderScreenList(items.getBrandSliderScreenList());
            uploadToS3Bucket(file);
            brandSliderScreenRepository.save(items);
            return ResponseEntity.ok().body(brandSliderScreen);
        } else {
            brandSliderScreen = new BrandSliderScreen();
            brandSliderRequest = new BrandSliderRequest();
            brandSliderScreen.setMainTittle(mainTittle);
            brandSliderScreen.setBrandId(brandId);
            List<BrandSliderRequest> brandSliderScreenList = new ArrayList<>();
            brandSliderScreenList.add(new BrandSliderRequest(title, desc, s3Url + file.getOriginalFilename(), position));
            brandSliderScreen.setBrandSliderScreenList(brandSliderScreenList); // Add the new instance

            uploadToS3Bucket(file);
            brandSliderScreenRepository.save(brandSliderScreen);
            return ResponseEntity.ok().body(brandSliderScreen);
        }
    }

    private void uploadToS3Bucket(MultipartFile img) {
        try {
            File file = convertMultiPartToFile(img);
            String fileName = img.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

            // The S3 URL of the uploaded image can be constructed using the bucketName and fileName
            String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            System.out.println("Image uploaded and accessible at: " + s3Url);

            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
