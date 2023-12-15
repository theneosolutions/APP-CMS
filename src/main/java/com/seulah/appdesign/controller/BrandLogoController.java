package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandLogoService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/cms/brandLogo")
public class BrandLogoController {
    private final BrandLogoService brandLogoService;

    public BrandLogoController(BrandLogoService brandLogoService) {
        this.brandLogoService = brandLogoService;
    }


    @PostMapping("/uploadLogo")
    public ResponseEntity<MessageResponse> saveBrandingLogo(@RequestPart MultipartFile file, @RequestParam String brandId,@RequestParam int height,@RequestParam int width) throws IOException {
        return brandLogoService.saveBrandingLogo(file, brandId,height,width);
    }

    @GetMapping("/getLogoByBrandId")
    public ResponseEntity<Resource> getLogoByBrandId(@RequestParam String brandId) {
        return brandLogoService.getLogoByBrandId(brandId);
    }

}
