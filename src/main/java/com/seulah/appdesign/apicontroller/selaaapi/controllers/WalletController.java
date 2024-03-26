package com.seulah.appdesign.apicontroller.selaaapi.controllers;

import com.seulah.appdesign.apicontroller.selaaapi.dto.TopUpWalletAmount;
import com.seulah.appdesign.apicontroller.selaaapi.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/selaApi/")
@CrossOrigin(origins = {"http://localhost:3000", "https://dev-dms.dd3kk1j719cpv.amplifyapp.com/", "http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
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
