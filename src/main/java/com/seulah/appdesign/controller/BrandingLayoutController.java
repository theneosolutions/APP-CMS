package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.BrandingLayoutRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandingLayoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<MessageResponse> createBrandingLayout(@RequestPart MultipartFile icon, @RequestPart BrandingLayoutRequest brandingLayoutRequest) throws IOException {
        return brandingLayoutService.createBrandingLayout(icon, brandingLayoutRequest);
    }
    @DeleteMapping("/deleteBrandingLayout")
    public ResponseEntity<MessageResponse> deleteBrandingLayout(@RequestParam String id) {
        return brandingLayoutService.deleteBrandingLayout(id);
    }

    @GetMapping("/getBrandingLayoutIconById")
    public ResponseEntity<Resource> getBrandingLayoutIconById(@RequestParam String id) throws MalformedURLException {
        return brandingLayoutService.getBrandingLayoutIconById(id);
    }
    @GetMapping("/getBrandingLayoutById")
    public ResponseEntity<MessageResponse> getBrandingLayoutById(@RequestParam String id)  {
        return brandingLayoutService.getBrandingLayoutById(id);
    }
}
