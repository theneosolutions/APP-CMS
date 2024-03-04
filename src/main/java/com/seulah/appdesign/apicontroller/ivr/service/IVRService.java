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
        // Define the URL with parameters
        String url = "https://autodialer.bevatel.com/autodialer/api/call.php?username=" + username + "&password=" + password + "&phone=" + phone;

        // Make the HTTP GET request and store the response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Print the response body
        System.out.println("Response body: " + responseEntity.getBody());
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
