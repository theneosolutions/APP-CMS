package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.CompanyBrand;
import com.seulah.appdesign.repository.CompanyBrandRepository;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyBrandService {
    private final CompanyBrandRepository companyBrandRepository;

    public CompanyBrandService(CompanyBrandRepository companyBrandRepository) {
        this.companyBrandRepository = companyBrandRepository;
    }

    public ResponseEntity<MessageResponse> createCompanyBrand(String companyId, String brandId) {
        CompanyBrand companyBrand = new CompanyBrand();
        companyBrand.setBrandId(brandId);
        companyBrand.setCompanyId(companyId);

        return new ResponseEntity<>(new MessageResponse("Successfully Created Banner", companyBrandRepository.save(companyBrand), false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getByBrandId(String brandId) {
        Optional<CompanyBrand> companyBrand = companyBrandRepository.findByBrandId(brandId);
        if (companyBrand.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", companyBrand, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<CompanyBrand> companyBrandList = companyBrandRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", companyBrandList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteByBrandId(String brandId) {
        Optional<CompanyBrand> companyBrand = companyBrandRepository.findByBrandId(brandId);
        if (companyBrand.isPresent()) {
            companyBrandRepository.save(companyBrand.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);

    }
}
