package com.seulah.appdesign.apicontroller.nafath;

import com.seulah.appdesign.apicontroller.nafath.entity.Nafath;
import com.seulah.appdesign.apicontroller.nafath.entity.NafathResponse;
import com.seulah.appdesign.apicontroller.nafath.request.NafathRequest;
import com.seulah.appdesign.apicontroller.nafath.service.NafathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class NafathController {
    private final NafathService nafathService;

    public NafathController(NafathService nafathService) {
        this.nafathService = nafathService;
    }

    @PostMapping("/nafath")
    public ResponseEntity<?> verificationByNafath(@RequestParam String local,
                                       @RequestParam String requestId,
                                       @RequestBody NafathRequest nafathRequest) {
        return nafathService.getRequestData(local, requestId, nafathRequest);
    }

    @PostMapping("/getNafathDetails")
    public ResponseEntity<Object> callBackUrlByNafath(@RequestBody NafathResponse nafathResponse) {
        return nafathService.saveResponse(nafathResponse);
    }
    @GetMapping("/getNafathDetails")
    public ResponseEntity<Object> getDetailsByNafath() {
        return nafathService.getDetailsByNafath();
    }
}
