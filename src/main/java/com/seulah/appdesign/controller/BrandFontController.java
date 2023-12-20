package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandFont")
public class BrandFontController {
    private final BrandFontService brandFontService;

    public BrandFontController(BrandFontService brandFontService) {
        this.brandFontService = brandFontService;
    }


    @PostMapping("/brandingFont")
    public ResponseEntity<MessageResponse> saveBrandingFont(@RequestBody List<Map<String, MultipartFile>> files, @RequestParam String brandId) {
        return brandFontService.saveBrandingFont(files, brandId);
    }

    @GetMapping("/brandFonts/getFontById")
    public ResponseEntity<byte[]> getBrandFontsByBrandId(@RequestParam String brandId) {
        return brandFontService.getFontFileUrlByBrandId(brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandFontService.deleteById(id);
    }


}
