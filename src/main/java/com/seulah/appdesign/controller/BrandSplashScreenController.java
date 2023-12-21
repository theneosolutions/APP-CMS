package com.seulah.appdesign.controller;


import com.seulah.appdesign.request.BrandSliderRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandSplashScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandSplashScreen")
public class BrandSplashScreenController {
    private final BrandSplashScreenService brandSplashScreenService;

    public BrandSplashScreenController(BrandSplashScreenService brandSplashScreenService) {
        this.brandSplashScreenService = brandSplashScreenService;
    }


    @PostMapping("/brandingSplashScreen")
    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(@RequestPart(value = "file") MultipartFile splashScreenImage, @RequestParam String brandId) {
        return brandSplashScreenService.saveBrandingSplashScreen(splashScreenImage, brandId);
    }

    @PostMapping("/brandingSliderScreen")
    public ResponseEntity<MessageResponse> saveBrandingSliderScreen(@RequestParam("mainTittle") String mainTittle, @RequestBody List<BrandSliderRequest> brandSliderScreenList, @RequestParam String brandId) {
        return brandSplashScreenService.saveBrandingSlidercreen(mainTittle, brandSliderScreenList, brandId);
    }


    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandSplashScreenService.deleteById(id);
    }

    @GetMapping("/brandSplashScreen/getById")
    public ResponseEntity<?> getBrandSplashScreenByBrandId(@RequestParam String brandId) {
        HashMap<String, String> responseMap = new HashMap<>();
        System.out.println(brandSplashScreenService.getBrandSplashScreenByBrandId(brandId));
        return ResponseEntity.ok().body(responseMap.put("SplashScreen", brandSplashScreenService.getBrandSplashScreenByBrandId(brandId)));
    }


}
