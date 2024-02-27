package com.seulah.appdesign.apicontroller.ivr.controller;

import com.seulah.appdesign.apicontroller.ivr.service.IVRService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class IVRController {
    private final IVRService ivrService;

    public IVRController(IVRService ivrService) {
        this.ivrService = ivrService;
    }

    @GetMapping("/callRequest")
    public void createRequest(@RequestParam String username,@RequestParam String password,@RequestParam String phone){
        ivrService.callRequest(username,password,phone);
    }
    @GetMapping("/confirmRequest")
    public ResponseEntity<?> confirmRequest(@RequestParam("status") int status,@RequestParam("mobile") String mobile){
        if(status!=0) {
          return ivrService.confirmRequest(status,mobile);
        }else {
            return  ivrService.confirmRequest(status,mobile);
        }
    }
}
