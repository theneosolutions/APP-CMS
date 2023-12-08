package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.BannerRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/banner")
@Slf4j
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveBanner(@RequestBody BannerRequest bannerRequest) {
        log.info("Saving Banner {}", bannerRequest);
        return bannerService.saveBanner(bannerRequest);
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

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestBody BannerRequest bannerRequest) {
        log.info("Update Banner {} By Id {}", bannerRequest, id);
        return bannerService.updateById(id, bannerRequest);
    }
}


