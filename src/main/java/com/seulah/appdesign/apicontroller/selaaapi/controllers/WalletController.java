package com.seulah.appdesign.apicontroller.selaaapi.controllers;

import com.seulah.appdesign.apicontroller.selaaapi.dto.TopUpWalletAmount;
import com.seulah.appdesign.apicontroller.selaaapi.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/selaApi/")
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<?> transactionRequest() {
        return service.getWalletBalance();
    }


    @PostMapping("/topupWalletAmount")
    public ResponseEntity<?> topupWalletAmountRequest(@RequestBody TopUpWalletAmount obj) {
        return service.topupWalletAmount(obj);
    }
}
