package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandSplashScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandSplashScreen")
public class BrandSplashScreenController {
    private final BrandSplashScreenService brandSplashScreenService;

    public BrandSplashScreenController(BrandSplashScreenService brandSplashScreenService) {
        this.brandSplashScreenService = brandSplashScreenService;
    }


    @PostMapping("/brandingSplashScreen")
    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(@RequestParam String splashScreen, @RequestParam String brandId) {
        return brandSplashScreenService.saveBrandingSplashScreen(splashScreen, brandId);
    }

    @GetMapping("/getSplashScreenByBrandId")
    public ResponseEntity<MessageResponse> getSplashScreenByBrandId(@RequestParam String brandId) {
        return brandSplashScreenService.getSplashScreenByBrandId(brandId);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return brandSplashScreenService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandSplashScreenService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestParam String splashScreen) {
        log.info("Update Splash Screen{} By Id {}", splashScreen, id);
        return brandSplashScreenService.updateById(id, splashScreen);
    }

}
