package com.seulah.appdesign.controller;

import com.seulah.appdesign.request.AppFlowRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.AppFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cms/appFlow")
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class AppFlowController {
    private final AppFlowService appFlowService;

    public AppFlowController(AppFlowService appFlowService) {
        this.appFlowService = appFlowService;
    }


    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveAppFlow(@RequestBody AppFlowRequest appFlowRequest) {
        log.info("Saving App Flow {}", appFlowRequest);
        return appFlowService.saveAppFlow(appFlowRequest);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAppFlowById(@RequestParam String id) {
        log.info("Get By Id: {}", id);
        return appFlowService.getAppFlowById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAll() {
        log.info("Get All ");
        return appFlowService.getAll();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return appFlowService.deleteById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateById(@RequestParam String id, @RequestBody AppFlowRequest appFlowRequest) {
        log.info("Update App Flow {} By Id {}", appFlowRequest, id);
        return appFlowService.updateById(id, appFlowRequest);
    }
}
