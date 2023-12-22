package com.seulah.appdesign.apicontroller.selaaapi.controllers;

import com.seulah.appdesign.apicontroller.selaaapi.service.DefaultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms/selaApi")
public class DefaultController {
    private final DefaultService defaultService;

    public DefaultController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }


    @GetMapping("/transactions")
    public ResponseEntity<?> transactionRequest() {
        return defaultService.getAllTransactions();
    }

    @GetMapping("/history")
    public ResponseEntity<?> historyRequest() {
        return defaultService.getAllHistory();
    }

}
