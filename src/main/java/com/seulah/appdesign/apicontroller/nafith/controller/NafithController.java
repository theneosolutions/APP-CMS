package com.seulah.appdesign.apicontroller.nafith.controller;

import com.seulah.appdesign.apicontroller.nafith.dto.SanadGroupDto;
import com.seulah.appdesign.apicontroller.nafith.model.NafithCreateSingleSanad;
import com.seulah.appdesign.apicontroller.nafith.service.NafithService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Nafith", description = "Nafith API v1")
@RestController
@RequestMapping("/api/v1/cms/nafith")
public class NafithController {

    @Autowired
    private NafithService nafithService;
    String trackingId, timestamp,signature;
    @PostMapping("/sanad-group")
    public ResponseEntity<?> setSanadGroup(@RequestBody Object sanad) {
        return nafithService.setSanadGroup(sanad);
    }

    @PostMapping("/getSanadDetails")
    public ResponseEntity<?> getSanadDetails(@RequestParam String groupUid,@RequestParam String sanadUid) {
        return ResponseEntity.ok(nafithService.getSanadDetails(groupUid,sanadUid));
    }
    @PostMapping("/callBackSanadDetails")
    public ResponseEntity<String> callBackSanadDetails(@RequestBody Object sanad) {
        nafithService.callBackSanadDetails(sanad);
        System.out.println(sanad);
        return ResponseEntity.ok("True");
    }
    @PostMapping("/downloadPDF")
    public ResponseEntity<?> downloadPDF(@RequestParam String uuid){
       return nafithService.downloadPDF(uuid);
    }

}
