package com.seulah.appdesign.apicontroller.nafath;

import com.seulah.appdesign.apicontroller.nafath.entity.Nafath;
import com.seulah.appdesign.apicontroller.nafath.service.NafathService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms")
public class NafathController {
   private final NafathService nafathService;

    public NafathController(NafathService nafathService) {
        this.nafathService = nafathService;
    }

    @RequestMapping("/nafath")
    public String verificationByNafath(@RequestParam String local,
                                       @RequestParam String requestId){
        nafathService.getRequestData(local,requestId);
    return "";
    }


}
