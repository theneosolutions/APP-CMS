package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.file.*;
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
    public ResponseEntity<MessageResponse> saveBrandingLogo(@RequestPart(value = "file") MultipartFile file, @RequestParam String brandId, @RequestParam int height, @RequestParam int width) throws IOException {
        return brandLogoService.saveBrandingLogo(file, brandId, height, width);
    }

    @GetMapping("/brandLogo/getLogoById")
    public ResponseEntity<?> getBrandLogoByBrandId(@RequestParam String brandId) throws NoSuchFileException {
        String base64Image = Base64.getEncoder().encodeToString(brandLogoService.getLogoFileUrlByBrandId(brandId));
        HashMap<String,String> logo = new HashMap();
        logo.put("logo",base64Image);
        return ResponseEntity.ok().body(logo);
    }
}
