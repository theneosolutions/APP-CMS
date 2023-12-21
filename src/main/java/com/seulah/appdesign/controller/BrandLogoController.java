package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandLogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;
import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/cms/brandLogo")
public class BrandLogoController {
    private final BrandLogoService brandLogoService;

    public BrandLogoController(BrandLogoService brandLogoService) {
        this.brandLogoService = brandLogoService;
    }


    @PostMapping("/uploadLogo")
    public ResponseEntity<MessageResponse> saveBrandingLogo(@RequestPart(value = "file") MultipartFile file, @RequestParam String brandId, @RequestParam int height, @RequestParam int width) {
        return brandLogoService.saveBrandingLogo(file, brandId, height, width);
    }

    @GetMapping("/brandLogo/getLogoById")
    public ResponseEntity<?> getBrandLogoByBrandId(@RequestParam String brandId) throws NoSuchFileException {
        String base64Image = Base64.getEncoder().encodeToString(brandLogoService.getLogoFileUrlByBrandId(brandId));
        HashMap<String, String> logo = new HashMap<>();
        logo.put("logo", base64Image);
        return ResponseEntity.ok().body(logo);
    }
}
