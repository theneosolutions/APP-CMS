package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandScreen")
public class BrandScreenController {
    private final BrandScreenService brandScreenService;

    public BrandScreenController(BrandScreenService brandScreenService) {
        this.brandScreenService = brandScreenService;
    }


    @PostMapping("/brandingScreen")
    public ResponseEntity<MessageResponse> saveBrandingScreen(@RequestParam List<String> screens, @RequestParam String brandId) {
        return brandScreenService.saveBrandingScreen(screens, brandId);
    }

    @GetMapping("/getScreenByBrandId")
    public ResponseEntity<MessageResponse> getScreenByBrandId(@RequestParam String brandId) {
        return brandScreenService.getScreenByBrandId(brandId);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return brandScreenService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandScreenService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestParam List<String> brandScreens) {
        log.info("Update Branding Screens{} By Id {}", brandScreens, id);
        return brandScreenService.updateById(id, brandScreens);
    }

}
