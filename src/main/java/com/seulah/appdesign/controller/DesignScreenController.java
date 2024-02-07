package com.seulah.appdesign.controller;


import com.seulah.appdesign.request.DesignScreenRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.DesignScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/designScreen")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","https://dev-cms.d3k8cagii9iejo.amplifyapp.com/","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class DesignScreenController {
    private final DesignScreenService designScreenService;

    public DesignScreenController(DesignScreenService designScreenService) {
        this.designScreenService = designScreenService;
    }


    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveDesignScreen(@RequestBody DesignScreenRequest designScreenRequest) {
        log.info("Saving Design Screen {}", designScreenRequest);
        return designScreenService.saveDesignScreen(designScreenRequest);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getDesignScreenById(@RequestParam String id) {
        log.info("Get By Id: {}", id);
        return designScreenService.getDesignScreenById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAllDesignScreen() {
        log.info("Get All ");
        return designScreenService.getAllDesignScreen();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteDesignScreenById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return designScreenService.deleteDesignScreenById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateDesignById(@RequestParam String id, @RequestBody DesignScreenRequest designScreenRequest) {
        log.info("Update Design Screen{} By Id {}", designScreenRequest, id);
        return designScreenService.updateDesignById(id, designScreenRequest);
    }
    @PutMapping(value = "/addComponentInScreen", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> addComponentInScreen(@RequestParam String screenId,@RequestParam String componentId) {
        log.info("Adding component in Screen By screenId {} and Component Id {}", screenId,componentId);
        return designScreenService.addComponentInScreen(screenId,componentId);
    }
    @PutMapping(value = "/deleteComponentFromScreen", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteComponentFromScreen(@RequestParam String screenId,@RequestParam String componentId) {
        log.info("Deleting component in Screen By screenId {} and Component Id {}", screenId,componentId);
        return designScreenService.deleteComponentFromScreen(screenId,componentId);
    }
}
