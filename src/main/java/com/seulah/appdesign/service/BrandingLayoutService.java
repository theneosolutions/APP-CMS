package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.BrandingLayout;
import com.seulah.appdesign.repository.BrandingLayoutRepository;
import com.seulah.appdesign.request.BrandingLayoutRequest;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Service
@Slf4j
public class BrandingLayoutService {
    private final BrandingLayoutRepository brandingLayoutRepository;

    private final BrandLogoService brandLogoService;

    public BrandingLayoutService(BrandingLayoutRepository brandingLayoutRepository, BrandLogoService brandLogoService) {
        this.brandingLayoutRepository = brandingLayoutRepository;
        this.brandLogoService = brandLogoService;
    }

    public ResponseEntity<MessageResponse> createBrandingLayout(MultipartFile icon, BrandingLayoutRequest brandingLayoutRequest) throws IOException {
        String fileExtension = brandLogoService.getFileExtension(icon);
        if (!fileExtension.equalsIgnoreCase("ico")) {
            log.error("Only ICO images are allowed.");
            return new ResponseEntity<>(new MessageResponse("Only ICO images are allowed. ", null, false), HttpStatus.BAD_REQUEST);
        }

        brandLogoService.saveToLocalDrive(icon);
        saveToDatabase(icon, brandingLayoutRequest);
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, BrandingLayoutRequest brandingLayoutRequest) {
        BrandingLayout brandingLayout = new BrandingLayout();
        brandingLayout.setHeight(brandingLayoutRequest.getHeight());
        brandingLayout.setWidth(brandingLayoutRequest.getWidth());
        brandingLayout.setLottieFiles(brandingLayoutRequest.getLottieFiles());
        brandingLayout.setIcon(file.getOriginalFilename());

        brandingLayoutRepository.save(brandingLayout);
        log.info("Branding logo saved to the database");
    }

    public ResponseEntity<MessageResponse> deleteBrandingLayout(String id) {
        Optional<BrandingLayout> optionalBrandingLayout = brandingLayoutRepository.findById(id);
        if (optionalBrandingLayout.isPresent()) {
            brandingLayoutRepository.delete(optionalBrandingLayout.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<Resource> getBrandingLayoutIconById(String id) throws MalformedURLException {
        Optional<BrandingLayout> optionalBrandingLayout = brandingLayoutRepository.findById(id);
        if (optionalBrandingLayout.isPresent()) {
            Resource resource = brandLogoService.loadFileAsResource(optionalBrandingLayout.get().getIcon());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getBrandingLayoutById(String id) {
        Optional<BrandingLayout> brandingLayout = brandingLayoutRepository.findById(id);
        return brandingLayout.map(layout -> new ResponseEntity<>(new MessageResponse("Success", layout, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));

    }
}
