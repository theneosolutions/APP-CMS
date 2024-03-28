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
    public ResponseEntity<?> addInstallmentCard(@RequestParam("file")  MultipartFile file,
                                                @RequestParam("title") String title,
                                                @RequestParam("desc")  String desc,
                                                @RequestParam("price") String price,
                                                @RequestParam("months") String months) {
        return installmentCardService.addInstallments(new InstallmentsEntity(title,desc,price,months), file);
    }

    @GetMapping("getAllCardInstallment")
    public ResponseEntity<?> getInstallmentCard() {
        return installmentCardService.getInstallments();
    }
}
