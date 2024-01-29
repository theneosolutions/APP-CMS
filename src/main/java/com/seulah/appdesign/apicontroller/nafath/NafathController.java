package com.seulah.appdesign.apicontroller.nafath;

import com.seulah.appdesign.apicontroller.nafath.entity.Nafath;
import com.seulah.appdesign.apicontroller.nafath.service.NafathService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class NafathController {
   private final NafathService nafathService;

    public NafathController(NafathService nafathService) {
        this.nafathService = nafathService;
    }

    @RequestMapping("/nafath")
    public String verificationByNafath(@RequestParam String local,
                                       @RequestParam String requestId){
     return  nafathService.getRequestData(local,requestId);
    }


}
