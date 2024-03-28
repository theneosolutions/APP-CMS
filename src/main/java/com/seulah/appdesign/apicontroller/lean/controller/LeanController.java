package com.seulah.appdesign.apicontroller.lean.controller;

import com.seulah.appdesign.apicontroller.lean.dto.LeanRequest;
import com.seulah.appdesign.apicontroller.lean.service.LeanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/lean")
public class LeanController {
    private final LeanService leanService;

    public LeanController(LeanService leanService) {
        this.leanService = leanService;
    }
    @PostMapping("/ibanValidate")
    public ResponseEntity<?> validate(@RequestBody LeanRequest leanRequest){
        return leanService.validateIban(leanRequest);
    }

}
