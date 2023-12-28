package com.seulah.appdesign.apicontroller.selaaapi.controllers;


import com.seulah.appdesign.apicontroller.selaaapi.request.OperationsBuyRequest;
import com.seulah.appdesign.apicontroller.selaaapi.request.OperationsTransferRequest;
import com.seulah.appdesign.apicontroller.selaaapi.service.OperationsBuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cms/selaApi")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class OperationsController {
    private final OperationsBuyService operationsBuyService;

    public OperationsController(OperationsBuyService operationsBuyService) {
        this.operationsBuyService = operationsBuyService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyRequest(@RequestBody OperationsBuyRequest operationsBuyRequest) {
        return operationsBuyService.getBuyDataFromApi(operationsBuyRequest);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferRequest(@RequestBody OperationsTransferRequest operationsTransferRequest) {
        System.out.println(operationsTransferRequest.toString());
        return operationsBuyService.getTransferDataFromApi(operationsTransferRequest);
    }

    @PostMapping("/redeem")
    public ResponseEntity<?> redeemRequest(@RequestParam("ownershipId") String ownershipId) {
        return operationsBuyService.getRedeemDataFromApi(ownershipId);
    }
}
