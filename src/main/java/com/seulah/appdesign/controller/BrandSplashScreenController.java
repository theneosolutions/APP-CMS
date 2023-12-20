package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.*;
import com.seulah.appdesign.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandSplashScreen")
public class BrandSplashScreenController {
    private final BrandSplashScreenService brandSplashScreenService;

    public BrandSplashScreenController(BrandSplashScreenService brandSplashScreenService) {
        this.brandSplashScreenService = brandSplashScreenService;
    }


    @PostMapping("/brandingSplashScreen")
    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(@RequestPart(value = "file") MultipartFile splashScreenImage,@RequestPart(value = "file2") MultipartFile splashScreen1,@RequestPart(value = "file3") MultipartFile splashScreen2,@RequestPart(value = "file4") MultipartFile splashScreen3, @RequestParam String brandId) {
        return brandSplashScreenService.saveBrandingSplashScreen(splashScreenImage,splashScreen1,splashScreen2,splashScreen3, brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandSplashScreenService.deleteById(id);
    }

    @GetMapping("/brandSplashScreen/getById")
    public ResponseEntity<?> getBrandSplashScreenByBrandId(@RequestParam String brandId) throws NoSuchFileException {
        HashMap<String,String> responseMap= new HashMap<>();
        System.out.println(brandSplashScreenService.getBrandSplashScreenByBrandId(brandId));
        return ResponseEntity.ok().body(responseMap.put("SplashScreen",brandSplashScreenService.getBrandSplashScreenByBrandId(brandId)));
    }


}
