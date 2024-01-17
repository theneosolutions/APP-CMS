package com.seulah.appdesign.apicontroller.gosi;

import com.seulah.appdesign.apicontroller.gosi.services.EmployeeStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms/gosi")
public class EmploymentStatusController {
    private final EmployeeStatusService employeeStatusService;

    public EmploymentStatusController(EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }
@GetMapping("/income")
    public ResponseEntity<?> getEmploymentStatusInfo(@RequestParam("APP-ID") String appId,
                                                     @RequestParam("APP-KEY") String appKey,
                                                     @RequestParam("PLATFORM-KEY") String platformKey,
                                                     @RequestParam("ORGANIZATION-NUMBER") String organizationNumber,
                                                     @RequestParam("customerId") String customerId) {
        return employeeStatusService.getStatusByCustomerId(appId, appKey, platformKey, organizationNumber, customerId);
    }
}



