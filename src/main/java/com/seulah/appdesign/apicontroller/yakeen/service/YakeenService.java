package com.seulah.appdesign.apicontroller.yakeen.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YakeenService {
    private final RestTemplate restTemplate;
    String baseUrl = "/api/v1";
    String appId;
    String appKey;
    String serviceKey;

    public YakeenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getRequestData(String id,String mobile){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID",appId);
        headers.set("APP-KEY",appKey);
        headers.set("SERVICE-KEY",serviceKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String uriTemplate = baseUrl + "/person?id={id}/owns-mobile/mobile-number={mobile}";

        ResponseEntity<String> response = restTemplate.exchange(
                uriTemplate,
                HttpMethod.POST,
                entity,
                String.class,
                id,
                mobile
        );
    }


}
