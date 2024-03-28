package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.BrandDetailResponse;
import com.seulah.appdesign.request.LayoutDetail;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.seulah.appdesign.utils.Constants.SUCCESS;


@Service
@Slf4j
public class BrandingService {
    private final BrandingRepository brandingRepository;

    private final BrandLogoService brandLogoService;

    private final BrandLogoRepository brandLogoRepository;
    private final BrandSplashScreenRepository brandSplashScreenRepository;
    private final BrandColorRepository brandColorRepository;

    private final BrandingLayoutService brandingLayoutService;
    private final BrandingLayoutRepository brandingLayoutRepository;

    public BrandingService(BrandingRepository brandingRepository, BrandLogoService brandLogoService, BrandLogoRepository brandLogoRepository, BrandSplashScreenRepository brandSplashScreenRepository, BrandColorRepository brandColorRepository, BrandingLayoutService brandingLayoutService, BrandingLayoutRepository brandingLayoutRepository) {
        this.brandingRepository = brandingRepository;
        this.brandLogoService = brandLogoService;
        this.brandLogoRepository = brandLogoRepository;
        this.brandSplashScreenRepository = brandSplashScreenRepository;
        this.brandColorRepository = brandColorRepository;
        this.brandingLayoutService = brandingLayoutService;
        this.brandingLayoutRepository = brandingLayoutRepository;
    }


    public ResponseEntity<MessageResponse> saveBranding(String brandingName, String langCode) {
        Branding branding = brandingRepository.findByBrandName(brandingName);
        if (branding != null) {
            log.info("Same brand name already exist in database");
            return new ResponseEntity<>(new MessageResponse("Brand Name Already Exist", null, false), HttpStatus.CONFLICT);
        }
        branding = new Branding();
        branding.setBrandName(brandingName);
        if (langCode == null || langCode.isEmpty()) {
            branding.setLanguageCode("en");
        } else {
            branding.setLanguageCode(langCode);
        }
        branding = brandingRepository.save(branding);
        log.info("brand saved successfully");
        return new ResponseEntity<>(new MessageResponse("Successfully Created App Design", branding, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getBrandingById(String id) {
        Optional<Branding> appDesign = brandingRepository.findById(id);
        return appDesign.map(design -> new ResponseEntity<>(new MessageResponse(SUCCESS, design, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Branding> brandingList = brandingRepository.findAll();
        return new ResponseEntity<>(new MessageResponse(SUCCESS, brandingList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Branding> appDesign = brandingRepository.findById(id);
        if (appDesign.isPresent()) {
            brandingRepository.delete(appDesign.get());
            return new ResponseEntity<>(new MessageResponse(SUCCESS, null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, String brandName, String langCode) {
        Optional<Branding> appDesignOptional = brandingRepository.findById(id);
        if (appDesignOptional.isEmpty()) {
            log.info("Data not found against the brand id {}", id);
            return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
        }
        Branding branding = appDesignOptional.get();
        if (brandName != null && !brandName.isEmpty()) {
            branding.setBrandName(brandName);
        }

        if (langCode != null && !langCode.isEmpty()) {
            branding.setLanguageCode(langCode);
        }

        branding = brandingRepository.save(branding);
        log.info("Brand update successfully");
        return new ResponseEntity<>(new MessageResponse("Successfully Updated", branding, false), HttpStatus.OK);
    }

    public ResponseEntity<BrandDetailResponse> getBrandDetail(String brandId) {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        Optional<BrandingColor> brandingColor = brandColorRepository.findByBrandId(brandId);
        List<BrandingLayout> brandingLayouts = brandingLayoutRepository.findAllByBrandId(brandId);
        BrandingLogo brandingLogo = brandLogoRepository.findByBrandId(brandId);

        byte[] brandLogo = brandLogoService.getLogoFileUrlByBrandId(brandId);
        Optional<BrandingSplashScreen> brandingSplashScreen = brandSplashScreenRepository.findByBrandId(brandId);
        String splashScreen = brandingSplashScreen.isPresent() ? brandingSplashScreen.get().getSplashScreen() : "";
        List<LayoutDetail> layoutDetails = new ArrayList<>();
        List<byte[]> iconContents = brandingLayoutService.getIconByBrandId(brandId);
        List<byte[]> lottieContents = brandingLayoutService.getLottieByBrandId(brandId);

        HashMap<String, Object> logoDetail = new HashMap<>();
        logoDetail.put("brandLogo", brandingLogo);
        logoDetail.put("brandLogoContent", brandLogo);

        int size = Math.min(iconContents.size(), lottieContents.size());
        for (int i = 0; i < size; i++) {
            LayoutDetail layoutDetail = new LayoutDetail();
            layoutDetail.setBrandId(brandingLayouts.get(i).getBrandId());
            layoutDetail.setIconContent(iconContents.get(i));
            layoutDetail.setLottieContent(lottieContents.get(i));
            layoutDetails.add(layoutDetail);
        }

        BrandDetailResponse brandDetailResponse = new BrandDetailResponse(
                brandId,
                branding.orElse(null),
                splashScreen,
                brandingColor.orElse(null),
                layoutDetails, logoDetail
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(brandDetailResponse);
    }


    public ResponseEntity<BrandDetailResponse> getBrandDetailByBrandName(String brandName) {
        Branding branding = brandingRepository.findByBrandName(brandName);
        if (branding != null) {
            return getBrandDetail(branding.getId());
        }
        return null;
    }

    public ResponseEntity<MessageResponse> getBrandsByLangCode(String languageCode) {
        List<Branding> brandingList = brandingRepository.findByLanguageCode(languageCode.toLowerCase());
        return new ResponseEntity<>(new MessageResponse(SUCCESS, brandingList, false), HttpStatus.OK);
    }
}
