package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandColorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandColor")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class BrandColorController {
    private final BrandColorService brandColorService;

    public BrandColorController(BrandColorService brandColorService) {
        this.brandColorService = brandColorService;
    }


    @PostMapping("/brandingColor")
    public ResponseEntity<MessageResponse> saveBrandingColor(@RequestBody List<Map<String, String>> colors, @RequestParam String brandId) {
        log.info("Saving brand Color {} against brand id :{}", colors, brandId);
        return brandColorService.saveBrandingColor(colors, brandId);
    }

    @GetMapping("/getColorByBrandId")
    public ResponseEntity<MessageResponse> getColorByBrandId(@RequestParam String brandId) {
        log.info("Getting brand color brand id :{}", brandId);
        return brandColorService.getColorByBrandId(brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandColorService.deleteById(id);
    }
}
