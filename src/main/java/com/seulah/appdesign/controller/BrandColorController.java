package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandColorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandColor")
public class BrandColorController {
    private final BrandColorService brandColorService;

    public BrandColorController(BrandColorService brandColorService) {
        this.brandColorService = brandColorService;
    }


    @PostMapping("/brandingColor")
    public ResponseEntity<MessageResponse> saveBrandingColor(@RequestParam List<String> colors, @RequestParam String brandId) {
        return brandColorService.saveBrandingColor(colors, brandId);
    }

    @GetMapping("/getColorByBrandId")
    public ResponseEntity<MessageResponse> getColorByBrandId(@RequestParam String brandId) {
        return brandColorService.getColorByBrandId(brandId);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return brandColorService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandColorService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestParam List<String> colors) {
        log.info("Update Branding Screens{} By Id {}", colors, id);
        return brandColorService.updateById(id, colors);
    }

}
