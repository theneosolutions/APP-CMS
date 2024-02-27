package com.seulah.appdesign.apicontroller.nafath.service;


import com.seulah.appdesign.apicontroller.nafath.entity.NafathResponse;
import com.seulah.appdesign.apicontroller.nafath.repo.NafathResponseRepo;
import com.seulah.appdesign.apicontroller.nafath.request.NafathRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NafathService {
    private final RestTemplate restTemplate;
    private final NafathResponseRepo nafathResponseRepo;
    String baseUrl = "https://nafath.api.elm.sa/stg/api/v1/mfa";

    ResponseEntity<String> getStatus, getjwk;

    public NafathService(RestTemplate restTemplate, NafathResponseRepo nafathResponseRepo) {
        this.restTemplate = restTemplate;
        this.nafathResponseRepo = nafathResponseRepo;
    }

    public  ResponseEntity<?> getRequestData(String local, String requestId, NafathRequest nafathRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID", "eb4ba20f");
        headers.set("APP-KEY", "9bb86fcc9488a1ed8c54185a4dd58005");
        HttpEntity<?> entity = new HttpEntity<>(nafathRequest, headers);
        String uriTemplate = baseUrl + "/request?local={local}&requestId={requestId}";


        ResponseEntity<Object> response = restTemplate.exchange(
                uriTemplate,
                HttpMethod.POST,
                entity,
                Object.class,
                local,
                requestId
        );
        System.out.println(response.getBody());

        return response;
    }

    public ResponseEntity<Object> saveResponse(NafathResponse nafathResponse) {
        if (nafathResponse != null) {
            System.out.println(nafathResponse.toString()+"Arham");
            return ResponseEntity.ok().body(nafathResponseRepo.save(nafathResponse));
        } else {
            return ResponseEntity.badRequest().body("No Record Found");
        }
    }
}
