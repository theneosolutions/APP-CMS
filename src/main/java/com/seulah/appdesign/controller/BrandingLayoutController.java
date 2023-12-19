package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

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
        return brandingLayoutService.createBrandingLayout(brandId, lottieFile);
    }

    @PostMapping("/createBrandingLayoutIcon")
    public ResponseEntity<MessageResponse> createBrandingLayoutIcon(@RequestParam String brandId, @RequestPart(value = "icon") MultipartFile icon) {
        return brandingLayoutService.createBrandingLayoutIcon(brandId, icon);
    }

    @DeleteMapping("/deleteBrandingLayout")
    public ResponseEntity<MessageResponse> deleteBrandingLayout(@RequestParam String id) {
        return brandingLayoutService.deleteBrandingLayout(id);
    }


    @GetMapping("/getBrandingLayoutById")
    public ResponseEntity<MessageResponse> getBrandingLayoutById(@RequestParam String id) {
        return brandingLayoutService.getBrandingLayoutById(id);
    }
}
