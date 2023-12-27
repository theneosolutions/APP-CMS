package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.CompanyBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/companyBrand")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class CompanyBrandController {
    private final CompanyBrandService companyBrandService;

    public CompanyBrandController(CompanyBrandService companyBrandService) {
        this.companyBrandService = companyBrandService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> createCompanyBrand(@RequestParam String companyId,@RequestParam String brandId) {
        log.info("Saving Company Brand brand Id{}, Company id{}", brandId,companyId);
        return companyBrandService.createCompanyBrand(companyId,brandId);
    }


    @GetMapping(value = "getByBrandId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getByBrandId(@RequestParam String brandId) {
        log.info("Get By Brand Id: {}", brandId);
        return companyBrandService.getByBrandId(brandId);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return companyBrandService.getAll();
    }

    @DeleteMapping(value = "/deleteByBrandId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteByBrandId(@RequestParam String brandId) {
        log.info("Delete By Id: {}", brandId);
        return companyBrandService.deleteByBrandId(brandId);
    }

}
