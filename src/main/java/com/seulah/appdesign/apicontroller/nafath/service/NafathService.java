package com.seulah.appdesign.apicontroller.nafath.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NafathService {
    private final RestTemplate restTemplate;
    String baseUrl = "/api/v1/mfa";
    String appId;

    ResponseEntity<String> getStatus,getjwk;
    public NafathService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getRequestData(String local, String requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID", appId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String uriTemplate = baseUrl + "/request?local={local}&requestId={requestId}";

        ResponseEntity<String> response = restTemplate.exchange(
                uriTemplate,
                HttpMethod.POST,
                entity,
                String.class,
                local,
                requestId
        );
        if (response.getBody() != null) {
            String url = baseUrl + "/request/status";
        getStatus = restTemplate.exchange(
                    uriTemplate,
                    HttpMethod.POST,
                    entity,
                    String.class,
                    local,
                    requestId
            );
            System.out.println(url);
        }
        else if(getStatus.getBody()!=null){
            String url = baseUrl + "/jwk";
            getjwk = restTemplate.exchange(
                    uriTemplate,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
        }
    }
}
