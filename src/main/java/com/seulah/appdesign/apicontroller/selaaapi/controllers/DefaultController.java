package com.seulah.appdesign.apicontroller.selaaapi.controllers;

import com.seulah.appdesign.apicontroller.selaaapi.service.DefaultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms/selaApi")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
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
