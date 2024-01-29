package com.seulah.appdesign.apicontroller.gosi.services;

import com.seulah.appdesign.apicontroller.gosi.dto.Gosi;
import com.seulah.appdesign.apicontroller.gosi.repo.GosiRepo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EmployeeStatusService {

    private final RestTemplate restTemplate;
    private final GosiRepo gosiRepo;

    public EmployeeStatusService(RestTemplate restTemplate, GosiRepo gosiRepo) {
        this.restTemplate = restTemplate;
        this.gosiRepo = gosiRepo;
    }

    public ResponseEntity<?> getStatusByCustomerId(String appId, String appKey, String platformKey, String organizationNumber, String customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("APP-ID", appId);
        headers.set("APP-KEY", appKey);
        headers.set("PLATFORM-KEY", platformKey);
        headers.set("ORGANIZATION-NUMBER", organizationNumber);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        // Build the URI with parameters if needed
        String apiUrl = "https://dakhli.api.elm.sa:443/api/v1/gosi/income/" + customerId;
        try {
            ResponseEntity<Gosi> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, Gosi.class);
            Gosi gosi = response.getBody();
            gosi.setId(customerId);
            gosiRepo.save(gosi);
            return response;
        } catch (RestClientException e) {
            // Handle exception, log it, or return an error response
            e.printStackTrace();
            return new ResponseEntity<>("Error during API call", HttpStatus.INTERNAL_SERVER_ERROR);
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