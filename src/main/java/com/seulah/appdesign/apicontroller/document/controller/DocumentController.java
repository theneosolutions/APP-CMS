package com.seulah.appdesign.apicontroller.document.controller;


import com.seulah.appdesign.apicontroller.document.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class DocumentController {



    @GetMapping("/doc")
    public ResponseEntity<?> doc(@RequestParam String userId) throws Exception {
        return null;
    }


}
