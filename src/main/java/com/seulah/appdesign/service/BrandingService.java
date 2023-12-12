package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.BrandingRequest;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BrandingService {
    private final BrandingRepository brandingRepository;

    public BrandingService(BrandingRepository brandingRepository) {
        this.brandingRepository = brandingRepository;
    }

    public ResponseEntity<MessageResponse> saveBranding(BrandingRequest brandingRequest) {
        Branding branding = new Branding();
        branding.setColor(brandingRequest.getColor());
        branding.setSplashScreen(brandingRequest.getSplashScreen());
        branding.setContent(brandingRequest.getContent());
        branding = brandingRepository.save(branding);

        return new ResponseEntity<>(new MessageResponse("Successfully Created App Design", branding, false), HttpStatus.CREATED);
    }


    public ResponseEntity<MessageResponse> getBrandingById(String id) {
        Optional<Branding> appDesign = brandingRepository.findById(id);
        return appDesign.map(design -> new ResponseEntity<>(new MessageResponse("Success", design, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Branding> brandingList = brandingRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", brandingList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Branding> appDesign = brandingRepository.findById(id);
        if (appDesign.isPresent()) {
            brandingRepository.delete(appDesign.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, BrandingRequest brandingRequest) {
        Optional<Branding> appDesignOptional = brandingRepository.findById(id);
        if (appDesignOptional.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
        }
        Branding branding = appDesignOptional.get();
        if (brandingRequest.getColor() != null && !brandingRequest.getColor().isEmpty()) {
            branding.setColor(brandingRequest.getColor());
        }
        if (brandingRequest.getSplashScreen() != null && !brandingRequest.getSplashScreen().isEmpty()) {
            branding.setSplashScreen(brandingRequest.getSplashScreen());
        }
        if (brandingRequest.getContent() != null && !brandingRequest.getContent().isEmpty()) {
            branding.setContent(brandingRequest.getContent());
        }

        branding = brandingRepository.save(branding);
        return new ResponseEntity<>(new MessageResponse("Successfully Updated", branding, false), HttpStatus.OK);
    }

}
