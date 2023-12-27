package com.seulah.appdesign.apicontroller.yakeen;

import com.seulah.appdesign.apicontroller.yakeen.service.YakeenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms")
public class YakeenController {
   private final YakeenService yakeenService;

    public YakeenController(YakeenService nafathService) {
        this.yakeenService = nafathService;
    }

    @RequestMapping("/yakeen")
    public String verificationByYakeen(@RequestParam String local,
                                       @RequestParam String requestId){
        yakeenService.getRequestData(local,requestId);
    return "";
    }


}
