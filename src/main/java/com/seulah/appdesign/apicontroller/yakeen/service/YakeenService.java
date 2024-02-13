package com.seulah.appdesign.apicontroller.yakeen.service;


import com.seulah.appdesign.apicontroller.yakeen.dto.YakeenDto;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YakeenService {
    private final RestTemplate restTemplate;
    String baseUrl = "https://yakeen-lite.api.elm.sa:443/api/v1/";

    private static final String appId = "83597d3b";
    private static final String appKey = "f611b6a0b405544534a5b0355862f701";
    private static final String serviceKey = "98fd9fd5-3ff7-4c28-a0bd-d4b8de7c8c78";

    public YakeenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> getRequestData(String id, String mobile) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID", appId);
        headers.set("APP-KEY", appKey);
        headers.set("SERVICE_KEY", serviceKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String uriTemplate = baseUrl + "person/" + id + "/owns-mobile/" + mobile;

        ResponseEntity<Object> response = restTemplate.exchange(
                uriTemplate,
                HttpMethod.GET,
                entity,
                Object.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            // Process the successful response
            Object responseBody = response.getBody();
            return ResponseEntity.ok().body(responseBody);
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            // Handle the specific error for invalid mobile number
            System.out.println("Invalid mobile number. Please provide a valid mobile number.");
            return ResponseEntity.ok().body("Invalid mobile number. Please provide a valid mobile number.");
        } else {
            // Handle other HTTP status codes as needed
            System.out.println("Unexpected error. Status code: " + response.getStatusCode());
            return ResponseEntity.badRequest().body("Unexpected error. Status code:");
        }
    }

}
