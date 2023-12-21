package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;

@RestController
@RequestMapping("/api/v1/cms/banner")
@Slf4j
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveBanner(@RequestParam String bannerDesign, @RequestPart MultipartFile bannerImage, @RequestParam int height, @RequestParam int width) {
        log.info("Saving Banner {} , height {}, width {}", bannerDesign, height, width);
        return bannerService.saveBanner(bannerImage, bannerDesign, height, width);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getBannerByID(@RequestParam String id) {
        log.info("Get By Id: {}", id);
        return bannerService.getBannerByID(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return bannerService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return bannerService.deleteById(id);
    }

    @GetMapping("/getBannerImageById")
    public byte[] getBannerImageById(@RequestParam String id)  {
        return bannerService.getBannerImageById(id);
    }
}


