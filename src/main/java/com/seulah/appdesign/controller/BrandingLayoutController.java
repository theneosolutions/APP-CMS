package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandingLayoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/v1/cms/brandingLayout")
@Slf4j
public class BrandingLayoutController {
    private final BrandingLayoutService brandingLayoutService;

    public BrandingLayoutController(BrandingLayoutService brandingLayoutService) {
        this.brandingLayoutService = brandingLayoutService;
    }

    @PostMapping("/createBrandingLayout")
    public ResponseEntity<MessageResponse> createBrandingLayout(@RequestParam String brandId, @RequestPart(value = "icon") MultipartFile icon, @RequestPart(value = "lottieFile") MultipartFile lottieFile) {
        return brandingLayoutService.createBrandingLayout(icon, brandId, lottieFile);
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
