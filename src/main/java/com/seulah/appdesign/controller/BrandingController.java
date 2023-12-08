package com.seulah.appdesign.controller;


import com.seulah.appdesign.request.BrandingRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/branding")
public class BrandingController {
    private final BrandingService brandingService;

    public BrandingController(BrandingService brandingService) {
        this.brandingService = brandingService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveBranding(@RequestBody BrandingRequest brandingRequest) {
        log.info("Saving Branding {}", brandingRequest);
        return brandingService.saveBranding(brandingRequest);
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
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestBody BrandingRequest brandingRequest) {
        log.info("Update Branding {} By Id {}", brandingRequest, id);
        return brandingService.updateById(id, brandingRequest);
    }

    @PutMapping(value = "/deleteLogo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteLogo(@RequestParam String id) {
        log.info("Delete logo from Branding By Id {}" , id);
        return brandingService.deleteLogo(id);
    }
}
