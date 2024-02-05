package com.seulah.appdesign.controller;

import com.seulah.appdesign.entity.InstallmentsEntity;
import com.seulah.appdesign.service.InstallmentCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cms/")
public class DefaultInstallmentsController {
    private final InstallmentCardService installmentCardService;

    public DefaultInstallmentsController(InstallmentCardService installmentCardService) {
        this.installmentCardService = installmentCardService;
    }

    @PostMapping("addCardInstallment")
    public ResponseEntity<?> addInstallmentCard(@RequestBody InstallmentsEntity installmentsEntity, @RequestParam MultipartFile file) {
        return installmentCardService.addInstallments(installmentsEntity, file);
    }

    @GetMapping("getAllCardInstallment")
    public ResponseEntity<?> getInstallmentCard() {
        return installmentCardService.getInstallments();
    }
}
