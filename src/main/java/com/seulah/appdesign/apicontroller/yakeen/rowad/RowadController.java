package com.seulah.appdesign.apicontroller.yakeen.rowad;



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/yakeen/rowad")
public class RowadController {

    private final RowadService rowadService;

    public RowadController(RowadService rowadService) {
        this.rowadService = rowadService;
    }

    @GetMapping("/personInfo")
    public Object getPersonInfo(
            @RequestParam("app-id") String appId,
            @RequestParam("app-key") String appKey,
            @RequestParam("operator-id") String operatorId,
            @RequestParam("otp") String otp,
            @RequestParam String personId) {
        return rowadService.getPersonInfo(appId, appKey, operatorId, otp, personId);

    }
}
