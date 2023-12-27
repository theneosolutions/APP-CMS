package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandingLayoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cms/brandingLayout")
@Slf4j
public class BrandingLayoutController {
    private final BrandingLayoutService brandingLayoutService;

    public BrandingLayoutController(BrandingLayoutService brandingLayoutService) {
        this.brandingLayoutService = brandingLayoutService;
    }

    @PostMapping("/createBrandingLayout")
    public ResponseEntity<MessageResponse> createBrandingLayout(@RequestParam String brandId, @RequestPart(value = "lottieFile") MultipartFile lottieFile) {
        log.info("Creating branding layout Lottie files{}", lottieFile.getOriginalFilename());
        return brandingLayoutService.createBrandingLayout(brandId, lottieFile);
    }

    @PostMapping("/createBrandingLayoutIcon")
    public ResponseEntity<MessageResponse> createBrandingLayoutIcon(@RequestParam String brandId, @RequestPart(value = "icon") MultipartFile icon) {
        log.info("Creating branding icon {}", icon.getOriginalFilename());
        return brandingLayoutService.createBrandingLayoutIcon(brandId, icon);
    }

    @DeleteMapping("/deleteBrandingLayout")
    public ResponseEntity<MessageResponse> deleteBrandingLayout(@RequestParam String id) {
        log.info("deleting branding layout by id {}", id);
        return brandingLayoutService.deleteBrandingLayout(id);
    }


    @GetMapping("/getBrandingLayoutById")
    public ResponseEntity<MessageResponse> getBrandingLayoutById(@RequestParam String id) {
        return brandingLayoutService.getBrandingLayoutById(id);
    }
}
