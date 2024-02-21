package com.seulah.appdesign.apicontroller.ivr.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IVRService {
    private final RestTemplate restTemplate;

    public IVRService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void callRequest(String username, String password,String phone){
        // Define the URL with parameters
        String url = "https://autodialer.bevatel.com/autodialer/api/call.php?username="+username+"&password="+password+"&phone="+phone;

        // Make the HTTP GET request and store the response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Print the response body
        System.out.println("Response body: " + responseEntity.getBody());
    }
}
