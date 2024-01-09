package com.seulah.appdesign.controller;


import com.seulah.appdesign.request.DesignComponentRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.service.DesignComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/cms/designComponent")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","https://main.d2j34yk77rmups.amplifyapp.com/","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class DesignComponentController {
    private final DesignComponentService designComponentService;

    public DesignComponentController(DesignComponentService designComponentService) {
        this.designComponentService = designComponentService;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveDesignComponent(@RequestBody DesignComponentRequest designComponentRequest) {
        log.info("Saving Design Component{}", designComponentRequest);
        return designComponentService.saveDesignComponent(designComponentRequest);
    }


    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getDesignComponentById(@RequestParam String id) {
        log.info("Get By Id: {}", id);
        return designComponentService.getDesignComponentById(id);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> getAllDesignComponent() {
        log.info("Get All ");
        return designComponentService.getAllDesignComponent();
    }

    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteDesignComponentById(@RequestParam String id) {
        log.info("Delete By Id: {}", id);
        return designComponentService.deleteDesignComponentById(id);
    }

    @PutMapping(value = "/updateById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateDesignComponentById(@RequestParam String id, @RequestBody DesignComponentRequest designComponentRequest) {
        log.info("Update Design {} By Id {}", designComponentRequest, id);
        return designComponentService.updateDesignComponentById(id, designComponentRequest);
    }
}
