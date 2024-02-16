package com.seulah.appdesign.apicontroller.oursms;

import com.seulah.appdesign.apicontroller.oursms.service.OursmsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class OursmsController {

    private final OursmsService oursmsService;

    public OursmsController(OursmsService oursmsService) {
        this.oursmsService = oursmsService;
    }

    @GetMapping("/sms")
    public String ourSMS(@RequestParam String idNumber, @RequestParam String otp){
        return otp;
       // return oursmsService.ourSms(idNumber,otp);
    }

}
