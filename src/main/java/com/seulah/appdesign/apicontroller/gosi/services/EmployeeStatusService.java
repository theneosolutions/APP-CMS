package com.seulah.appdesign.apicontroller.gosi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulah.appdesign.apicontroller.selaaapi.dto.OperationsTransferResponse;
import com.seulah.appdesign.apicontroller.selaaapi.request.OperationsTransferRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeStatusService {

    private final RestTemplate restTemplate;

    public EmployeeStatusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getStatusByCustomerId(String appId, String appKey, String platformKey, String organizationNumber, String customerId){
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("APP-ID", appId);
        headers.set("APP-KEY", appKey);
        headers.set("PLATFORM-KEY", platformKey);
        headers.set("ORGANIZATION-NUMBER", organizationNumber);
        headers.set("customerId", customerId);

        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/operations/transfer";
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class,
                customerId
        );
    }
}
