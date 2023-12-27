package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandFontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandFont")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class BrandFontController {
    private final BrandFontService brandFontService;

    public BrandFontController(BrandFontService brandFontService) {
        this.brandFontService = brandFontService;
    }


    @PostMapping("/brandingFont")
    public ResponseEntity<MessageResponse> saveBrandingFont(@RequestBody MultipartFile[] files, @RequestParam String brandId) {
        log.info("Saving branding font {} against brand id {}", files, brandId);
        return brandFontService.saveBrandingFont(files, brandId);
    }

    @GetMapping("/brandFonts/getFontById")
    public ResponseEntity<byte[]> getBrandFontsByBrandId(@RequestParam String brandId) {
        log.info("Getting brand font by brand id {} ", brandId);
        return brandFontService.getFontFileUrlByBrandId(brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandFontService.deleteById(id);
    }

    @PostMapping("/saveFontFamily")
    public ResponseEntity<MessageResponse> saveFontFamily(@RequestParam String brandId, @RequestBody Object response) {
        log.info("Saving Font family against brand id {}", response);
        new Thread(() -> brandFontService.saveFontFamily(brandId, response)).start();
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }


}
