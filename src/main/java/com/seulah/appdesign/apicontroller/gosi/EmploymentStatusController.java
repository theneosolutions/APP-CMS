package com.seulah.appdesign.apicontroller.gosi;

import com.seulah.appdesign.apicontroller.gosi.services.EmployeeStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/gosi")
public class EmploymentStatusController {
    private final EmployeeStatusService employeeStatusService;

    public EmploymentStatusController(EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }

    @GetMapping("/income")
    public ResponseEntity<?> addEmploymentStatusInfo(@RequestParam("customerId") String customerId) {
        return employeeStatusService.getStatusByCustomerId(customerId);
    }

    @GetMapping("/getDataById")
    public ResponseEntity<?> getEmploymentStatusInfo(@RequestParam("idNumber") String id) {
        return employeeStatusService.getData(id);
    }
}



