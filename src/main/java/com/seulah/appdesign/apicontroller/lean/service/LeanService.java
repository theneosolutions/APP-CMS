package com.seulah.appdesign.apicontroller.lean.service;

import com.seulah.appdesign.apicontroller.lean.dto.LeanRequest;
import com.seulah.appdesign.apicontroller.lean.model.LeanEntity;
import com.seulah.appdesign.apicontroller.lean.repo.LeanRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeanService {

    private final RestTemplate restTemplate;
    private final LeanRepo leanRepo;

    @Value("${application.leanToken}")
    private String token;

    public LeanService(RestTemplate restTemplate, LeanRepo leanRepo) {
        this.restTemplate = restTemplate;
        this.leanRepo = leanRepo;
    }

    public ResponseEntity<?> validateIban(LeanRequest leanRequest){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "*/*");
        headers.set("lean-app-token", token);


        // Create the HttpEntity
        HttpEntity<?> entity = new HttpEntity<>(leanRequest, headers);

        // Make the POST request
        ResponseEntity<LeanEntity> response = restTemplate.exchange("https://sandbox.sa.leantech.me/verifications/v2/iban", HttpMethod.POST,
                entity, LeanEntity.class);
        System.out.println(response.getBody());
       LeanEntity lean = leanRepo.save(response.getBody());
        // Return the response body
        return ResponseEntity.ok().body(lean);
    }
}
