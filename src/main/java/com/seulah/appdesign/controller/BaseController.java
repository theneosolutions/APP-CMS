package com.seulah.appdesign.controller;

import com.seulah.appdesign.entity.AgreementForm;
import com.seulah.appdesign.entity.Terms;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cms/terms")
@Slf4j
public class BaseController {

    private final BaseService baseService;

    public BaseController(BaseService baseService) {
        this.baseService = baseService;
    }

    @PostMapping(value = "/saveTerms")
    public ResponseEntity<?> saveTerms(@RequestBody Terms terms) {
        log.info("Saving Terms {} ,  condition {}", terms.getTitle(),terms.getDesc());
        return baseService.saveTerms(terms);
    }
    @GetMapping(value = "/getTerms")
    public ResponseEntity<?> getTerms() {
        return baseService.getTerms();
    }

    @PostMapping(value = "/saveAgreement")
    public ResponseEntity<?> saveAgreement(@RequestBody AgreementForm agreementForm) {
        log.info("Saving Terms {} ,  condition {}", agreementForm.getTitle(),agreementForm.getDesc());
        return baseService.saveAgreement(agreementForm);
    }
    @GetMapping(value = "/getAgreement")
    public ResponseEntity<?> getAgreement() {
        return baseService.getAgreement();
    }
}
