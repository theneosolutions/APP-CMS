package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.file.*;

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
    public byte[] getBrandLogoByBrandId(@RequestParam String brandId) throws NoSuchFileException {
        return brandLogoService.getLogoFileUrlByBrandId(brandId);
    }



}
