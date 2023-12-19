package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

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
    public String getBrandLogoByBrandId(@RequestParam String brandId) {
        return brandLogoService.getLogoFileUrlByBrandId(brandId);
    }

//    @GetMapping("/getLogoByBrandId")
//    public ResponseEntity<Resource> getLogoByBrandId(@RequestParam String brandId) {
//        return brandLogoService.getLogoByBrandId(brandId);
//    }

}
