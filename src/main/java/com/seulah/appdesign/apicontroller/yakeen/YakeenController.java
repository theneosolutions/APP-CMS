package com.seulah.appdesign.apicontroller.yakeen;

import com.seulah.appdesign.apicontroller.yakeen.service.YakeenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class YakeenController {
    private final YakeenService yakeenService;

    public YakeenController(YakeenService nafathService) {
        this.yakeenService = nafathService;
    }

    @RequestMapping("/yakeen")
    public ResponseEntity<?> verificationByYakeen(@RequestParam String idNumber,
                                                  @RequestParam String mobile) {
        return yakeenService.getRequestData(idNumber, mobile);
    }


}
