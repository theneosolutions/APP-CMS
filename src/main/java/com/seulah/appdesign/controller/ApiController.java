package com.seulah.appdesign.controller;

import com.seulah.appdesign.dto.ScreenDto;
import com.seulah.appdesign.request.AppFlowRequest;
import com.seulah.appdesign.service.ApiFlowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cms/apiFlow")
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","https://main.d2j34yk77rmups.amplifyapp.com/","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
@Tag(name ="ApiController")
public class ApiController {

    private final ApiFlowService apiFlowService;

    public ApiController(ApiFlowService apiFlowService) {
        this.apiFlowService = apiFlowService;
    }

    @PostMapping(value = "/saveApiFlow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveApiFLow(@RequestParam String brandId, @RequestBody Object apiFlowRequest) {
        log.info("Saving Api Flow {}, brandId {}", apiFlowRequest,brandId);
        return apiFlowService.saveApiFlow(brandId,apiFlowRequest);
    }

    @PostMapping(value = "/saveAppFlow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAppFlow(@RequestParam String brandId, @RequestBody List<ScreenDto> screenDtos) {
        log.info("Saving App Flow {}", screenDtos);
        return apiFlowService.saveAppFlow(screenDtos,brandId);
    }
    @PatchMapping(value = "/updateAppFlow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAppFlow(@RequestParam String brandId, @RequestBody List<ScreenDto> screenDtos) {
        log.info("Saving App Flow {}", screenDtos);
        return apiFlowService.updateAppFlow(screenDtos,brandId);
    }
    @GetMapping(value = "/getAppFlow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAppFlow(@RequestParam String brandId) {
        log.info("Saving App Flow {}", brandId);
        return apiFlowService.getAppFlow(brandId);
    }
}
