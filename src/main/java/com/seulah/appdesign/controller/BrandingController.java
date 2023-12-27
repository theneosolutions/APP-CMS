package com.seulah.appdesign.controller;


import com.seulah.appdesign.request.BrandDetailResponse;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/branding")
public class BrandingController {
    private final BrandingService brandingService;

    public BrandingController(BrandingService brandingService) {
        this.brandingService = brandingService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveBranding(@RequestParam String brandName) {
        log.info("Saving Branding {}", brandName);
        return brandingService.saveBranding(brandName);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getBrandingById(@RequestParam String id) {
        log.info("Get By Id: {}", id);
        return brandingService.getBrandingById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return brandingService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandingService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestParam String brandName) {
        log.info("Update Branding {} By Id {}", brandName, id);
        return brandingService.updateById(id, brandName);
    }

    @GetMapping(value = "/getBrandDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDetailResponse> getBrandDetail(@RequestParam String id) {
        log.info("Getting Branding Detail By Id {}", id);
        return brandingService.getBrandDetail(id);
    }

    @GetMapping(value = "/getBrandDetailByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDetailResponse> getBrandDetailByBrandName(@RequestParam String brandName) throws IOException {
        log.info("Getting Branding Detail By Brand Name {}", brandName);
        return brandingService.getBrandDetailByBrandName(brandName);
    }

}
