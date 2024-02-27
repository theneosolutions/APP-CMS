package com.seulah.appdesign.apicontroller.nafith.controller;

import com.seulah.appdesign.apicontroller.nafith.dto.SanadGroupDto;
import com.seulah.appdesign.apicontroller.nafith.service.NafithService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Nafith", description = "Nafith API v1")
@RestController
@RequestMapping("/api/nafith")
public class NafithController {

    @Autowired
    private NafithService nafithService;
    String trackingId, timestamp,signature;
    @PostMapping("/sanad-group")
    public ResponseEntity<String> setSanadGroup() {
        nafithService.setSanadGroup(trackingId, timestamp, signature,new SanadGroupDto());
        return ResponseEntity.ok("Sanad Group created successfully");
    }
}
