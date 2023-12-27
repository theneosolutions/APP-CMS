package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandLogoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/cms/brandLogo")
@Slf4j
public class BrandLogoController {
    private final BrandLogoService brandLogoService;

    public BrandLogoController(BrandLogoService brandLogoService) {
        this.brandLogoService = brandLogoService;
    }


    @PostMapping("/uploadLogo")
    public ResponseEntity<MessageResponse> saveBrandingLogo(@RequestPart(value = "file") MultipartFile file, @RequestParam String brandId, @RequestParam int height, @RequestParam int width) {
        log.info("Saving brand logo against brand id");
        return brandLogoService.saveBrandingLogo(file, brandId, height, width);
    }

    @GetMapping("/brandLogo/getLogoById")
    public ResponseEntity<?> getBrandLogoByBrandId(@RequestParam String brandId) {
        String base64Image = Base64.getEncoder().encodeToString(brandLogoService.getLogoFileUrlByBrandId(brandId));
        HashMap<String, String> logo = new HashMap<>();
        logo.put("logo", base64Image);
        return ResponseEntity.ok().body(logo);
    }
}
