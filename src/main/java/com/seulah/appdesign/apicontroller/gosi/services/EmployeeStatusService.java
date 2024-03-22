package com.seulah.appdesign.apicontroller.gosi.services;

import com.seulah.appdesign.apicontroller.gosi.dto.Gosi;
import com.seulah.appdesign.apicontroller.gosi.repo.GosiRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

@Service
public class EmployeeStatusService {

    private final RestTemplate restTemplate;
    private final GosiRepo gosiRepo;
    @Value("${spring.application.gosi.appId}")
    private String appId;
    @Value("${spring.application.gosi.appKey}")
    private String appKey;
    @Value("${spring.application.gosi.platformKey}")
    private String platformKey;
    @Value("${spring.application.gosi.organizationNumber}")
    private String organizationNumber;

    public EmployeeStatusService(RestTemplate restTemplate, GosiRepo gosiRepo) {
        this.restTemplate = restTemplate;
        this.gosiRepo = gosiRepo;
    }
    Gosi gosi= new Gosi();
    String urlLos="http://localhost:8091/api/v1/los/gosi/user?userId=";
    public HttpEntity<String> getHeader(String gosi){
        if(gosi==null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            return new HttpEntity<>(headers);
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("APP-ID", appId);
            headers.set("APP-KEY", appKey);
            headers.set("PLATFORM-KEY", platformKey);
            headers.set("ORGANIZATION-NUMBER", organizationNumber);
            return new HttpEntity<>(headers);
        }
    }
    public ResponseEntity<?> getStatusByCustomerId(String customerId, String userId) {


        // Build the URI with parameters if needed
        String apiUrl = "https://dakhli.api.elm.sa:443/api/v1/gosi/income/" + customerId;
        try {
            ResponseEntity<Gosi> response = restTemplate.exchange(apiUrl, HttpMethod.GET, getHeader("1"), Gosi.class);
            Gosi gosi = response.getBody();
            gosi.setId(customerId);
            gosi.setStatus("true");
            gosiRepo.save(gosi);
           restTemplate.exchange(urlLos+userId, HttpMethod.GET, getHeader(null), Gosi.class);
            return response;
        } catch (RestClientException e) {
            // Handle exception, log it, or return an error response
            e.printStackTrace();
           gosi.setStatus("false");
           gosi.setMessage("No Record Found From GOSI");
           gosi.setEmploymentStatusInfo(null);
            return ResponseEntity.ok().body(gosi);
        } catch (Exception e) {
            // Handle exception, log it, or return an error response
            e.printStackTrace();
            gosi.setStatus("false");
            gosi.setMessage("No Record Found From GOSI");
            gosi.setEmploymentStatusInfo(null);
            return new ResponseEntity<>(gosi, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getData(String id) {
        Optional<Gosi> gosi = gosiRepo.findById(id);
        if (gosi.isPresent()) {
            return ResponseEntity.ok().body(gosi);
        } else {
            return ResponseEntity.badRequest().body("No Record Found");
        }
    }
}