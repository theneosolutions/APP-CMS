package com.seulah.appdesign.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.seulah.appdesign.entity.BrandSliderScreen;
import com.seulah.appdesign.request.BrandSliderRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.request.SplashScreenResponse;
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
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","https://dev-cms.d3k8cagii9iejo.amplifyapp.com/","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class BrandSplashScreenController {
    private final BrandSplashScreenService brandSplashScreenService;

    private String fileContent;
    BrandSliderRequest brandSliderRequest = new BrandSliderRequest();
    BrandSliderScreen brandSliderScreen = new BrandSliderScreen();
    public BrandSplashScreenController(BrandSplashScreenService brandSplashScreenService) {
        this.brandSplashScreenService = brandSplashScreenService;
    }


    @PostMapping("/brandingSplashScreen")
    public ResponseEntity<MessageResponse> saveBrandingSplashScreen(@RequestPart(value = "file") MultipartFile splashScreenImage, @RequestParam String brandId) {
        log.info("Saving brand splash screen against brand id {}", brandId);
        return brandSplashScreenService.saveBrandingSplashScreen(splashScreenImage, brandId);
    }

    @PostMapping("/brandingSliderScreen")
    public ResponseEntity<?> saveBrandingSliderScreen(@RequestParam(value = "mainTittle") String mainTittle,
                                                                    @RequestParam(value = "brandId") String brandId,
                                                                    @RequestParam(value = "desc") String desc,
                                                                    @RequestParam(value = "title") String title,
                                                                    @RequestParam(value = "position") String position,
                                                                    @RequestParam(value = "file") MultipartFile file) throws IOException {


        log.info("saved brand slider screen against brand id {}", brandId);
        return brandSplashScreenService.saveAllSliders(mainTittle,brandId,desc,title,
                position, file);
       // return brandSplashScreenService.saveBrandingSliderScreen(brandSliderScreen,position);
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
    public ResponseEntity<?> getBrandSplashScreenByBrandId(@RequestParam String brandId)  {
        return ResponseEntity.ok().body(new SplashScreenResponse(brandSplashScreenService.getBrandSplashScreenByBrandId(brandId)));
    }


}
