package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandColor")
public class BrandColorController {
    private final BrandColorService brandColorService;

    public BrandColorController(BrandColorService brandColorService) {
        this.brandColorService = brandColorService;
    }


    @PostMapping("/brandingColor")
    public ResponseEntity<MessageResponse> saveBrandingColor(@RequestBody List<Map<String, String>> colors, @RequestParam String brandId) {
        return brandColorService.saveBrandingColor(colors, brandId);
    }

    @GetMapping("/getColorByBrandId")
    public ResponseEntity<MessageResponse> getColorByBrandId(@RequestParam String brandId) {
        return brandColorService.getColorByBrandId(brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandColorService.deleteById(id);
    }


}
