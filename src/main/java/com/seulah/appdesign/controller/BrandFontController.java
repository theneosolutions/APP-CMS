package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandFont")
public class BrandFontController {
    private final BrandFontService brandFontService;

    public BrandFontController(BrandFontService brandFontService) {
        this.brandFontService = brandFontService;
    }


    @PostMapping("/brandingFont")
    public ResponseEntity<MessageResponse> saveBrandingFont(@RequestPart MultipartFile fontFile, @RequestParam String brandId) {
        return brandFontService.saveBrandingFont(fontFile, brandId);
    }
    @GetMapping("/brandFonts/getFontById")
    public byte[] getBrandFontsByBrandId(@RequestParam String brandId) throws NoSuchFileException {
        return brandFontService.getFontFileUrlByBrandId(brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandFontService.deleteById(id);
    }


}
