package com.seulah.appdesign.apicontroller.yakeen.rowad;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RowadService {
   private final RestTemplate restTemplate;
   private final PersonInfoRepo personInfoRepo;

    public RowadService(RestTemplate restTemplate, PersonInfoRepo personInfoRepo) {
        this.restTemplate = restTemplate;
        this.personInfoRepo = personInfoRepo;
    }

    public Object getPersonInfo(String appId, String appKey, String operatorId, String otp, String personId){
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("APP-ID", appId);
        headers.set("APP-KEY", appKey);
        headers.set("operator-id", operatorId);
        headers.set("otp", otp);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        // Build the URI with parameters if needed
        String apiUrl = "https://yakeen-rowad.api.elm.sa:443/api/v3/rowad/inquiry/person-info/" + personId;
        try {
            ResponseEntity<RowadPersonInfo> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, RowadPersonInfo.class);
            personInfoRepo.save(response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            // Handle exception, log it, or return an error response
            e.printStackTrace();
            return new ResponseEntity<>("Error during API call", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
