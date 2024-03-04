package com.seulah.appdesign.apicontroller.ivr.service;

import com.seulah.appdesign.apicontroller.ivr.model.IVRStatus;
import com.seulah.appdesign.apicontroller.ivr.repo.IVRRepo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.Collections;

@Service
public class IVRService {
    private final RestTemplate restTemplate;
    private final IVRRepo ivrRepo;

    public IVRService(RestTemplate restTemplate, IVRRepo ivrRepo) {
        this.restTemplate = restTemplate;
        this.ivrRepo = ivrRepo;
    }

    public void callRequest(String username, String password, String phone) {

        // Make the HTTP GET request and store the response
        String url = UriComponentsBuilder.fromHttpUrl("https://autodialer.bevatel.com/autodialer/api/call.php")
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("phone", phone)
                .toUriString();

        // Perform the GET request
        String response = restTemplate.getForObject(url, String.class);

        // Print the response body
        System.out.println("Response body: " + response);
    }

    public ResponseEntity<?> confirmRequest(int status, String mobile) {
        System.out.println("Response body confirmRequest{}: " + status+mobile);
        if(status==1) {
            return ResponseEntity.ok().body(ivrRepo.save(new IVRStatus(status, mobile)));
        }else {
            return ResponseEntity.ok().body(ivrRepo.save(new IVRStatus(status, mobile)));
        }
    }


}
