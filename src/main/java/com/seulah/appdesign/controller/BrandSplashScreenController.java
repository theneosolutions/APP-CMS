package com.seulah.appdesign.controller;


import com.seulah.appdesign.entity.BrandSliderScreen;
import com.seulah.appdesign.request.BrandSliderRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BrandSplashScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/v1/cms/brandSplashScreen")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class BrandSplashScreenController {
    private final BrandSplashScreenService brandSplashScreenService;

    public BrandSplashScreenController(BrandSplashScreenService brandSplashScreenService) {
        this.brandSplashScreenService = brandSplashScreenService;
    }


    @PostMapping("/brandingSplashScreen")
    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(@RequestPart(value = "file") MultipartFile splashScreenImage, @RequestParam String brandId) {
        log.info("Saving brand splash screen against brand id {}", brandId);
        return brandSplashScreenService.saveBrandingSplashScreen(splashScreenImage, brandId);
    }

    @PostMapping("/brandingSliderScreen")
    public ResponseEntity<MessageResponse> saveBrandingSliderScreen(@RequestParam(value = "mainTittle") String mainTittle, @RequestParam(value = "brandId") String brandId, @RequestParam(value = "desc") String desc, @RequestParam(value = "title") String title, @RequestParam(value = "position") String position, @RequestParam(value = "file") MultipartFile brandSliderScreenList) throws IOException {
        byte[] fileBytes = brandSliderScreenList.getBytes();
        String fileContent = new String(fileBytes);

        BrandSliderRequest brandSliderRequest = new BrandSliderRequest(title, desc, fileContent, position);
        List<BrandSliderRequest> brandSliderRequests = new ArrayList<>();
        brandSliderRequests.add(brandSliderRequest);
        BrandSliderScreen brandSliderScreen = new BrandSliderScreen(mainTittle, brandSliderRequests, brandId);
        log.info("saved brand slider screen against brand id {}", brandId);
        return brandSplashScreenService.saveBrandingSliderScreen(brandSliderScreen);
    }

    @GetMapping("/brandSliderScreen/getById")
    public ResponseEntity<?> getBrandSliderScreenByBrandId(@RequestParam String brandId) {
        return ResponseEntity.ok().body(brandSplashScreenService.getBrandSliderScreenByBrandId(brandId));
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return brandSplashScreenService.deleteById(id);
    }

    @GetMapping("/brandSplashScreen/getById")
    public ResponseEntity<?> getBrandSplashScreenByBrandId(@RequestParam String brandId) {
        HashMap<String, String> responseMap = new HashMap<>();
        return ResponseEntity.ok().body(responseMap.put("SplashScreen", brandSplashScreenService.getBrandSplashScreenByBrandId(brandId)));
    }


}
