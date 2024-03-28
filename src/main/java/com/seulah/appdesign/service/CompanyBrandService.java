package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.CompanyBrand;
import com.seulah.appdesign.repository.CompanyBrandRepository;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyBrandService {
    private final CompanyBrandRepository companyBrandRepository;

    public CompanyBrandService(CompanyBrandRepository companyBrandRepository) {
        this.companyBrandRepository = companyBrandRepository;
    }

    public ResponseEntity<MessageResponse> createCompanyBrand(String companyId, String brandId) {
        CompanyBrand companyBrand = new CompanyBrand();
        companyBrand.setBrandId(brandId);
        companyBrand.setCompanyId(companyId);
        log.info("Saved Company brand successfully");
        return new ResponseEntity<>(new MessageResponse("Successfully Created Banner", companyBrandRepository.save(companyBrand), false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getByBrandId(String brandId) {
        Optional<CompanyBrand> companyBrand = companyBrandRepository.findByBrandId(brandId);
        return companyBrand.map(brand -> new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, brand, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<CompanyBrand> companyBrandList = companyBrandRepository.findAll();
        log.info("Getting company brand list successfully");
        return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, companyBrandList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteByBrandId(String brandId) {
        Optional<CompanyBrand> companyBrand = companyBrandRepository.findByBrandId(brandId);
        if (companyBrand.isPresent()) {
            companyBrandRepository.save(companyBrand.get());
            log.info("Data delete successfully");
            return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, null, false), HttpStatus.OK);
        }
        log.info("Data not exist against this brand id : {}", brandId);
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);

    }
}
