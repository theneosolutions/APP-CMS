package com.seulah.appdesign.apicontroller.nafath.service;


import com.seulah.appdesign.apicontroller.nafath.entity.NafathPayload;
import com.seulah.appdesign.apicontroller.nafath.entity.NafathResponse;
import com.seulah.appdesign.apicontroller.nafath.repo.NafathPayloadRepo;
import com.seulah.appdesign.apicontroller.nafath.repo.NafathResponseRepo;
import com.seulah.appdesign.apicontroller.nafath.request.NafathRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.StringTokenizer;

@Service
public class NafathService {
    private final RestTemplate restTemplate;
    private final NafathResponseRepo nafathResponseRepo;
    private final NafathPayloadRepo nafathPayloadRepo;
    String baseUrl = "https://nafath.api.elm.sa/api/v1/mfa";

    @Value("${spring.application.nafath.appId}")
    private String appId;
    @Value("${spring.application.nafath.appKey}")
    private String appKey;

    public NafathService(RestTemplate restTemplate, NafathResponseRepo nafathResponseRepo, NafathPayloadRepo nafathPayloadRepo) {
        this.restTemplate = restTemplate;
        this.nafathResponseRepo = nafathResponseRepo;
        this.nafathPayloadRepo = nafathPayloadRepo;
    }

    ResponseEntity<Object> response;

    public ResponseEntity<?> getRequestData(String local, String requestId, NafathRequest nafathRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID", appId);
        headers.set("APP-KEY", appKey);
        HttpEntity<?> entity = new HttpEntity<>(nafathRequest, headers);
        String uriTemplate = baseUrl + "/request?local={local}&requestId={requestId}";

        try {
            response = restTemplate.exchange(
                    uriTemplate,
                    HttpMethod.POST,
                    entity,
                    Object.class,
                    local,
                    requestId
            );
            System.out.println(appId+appKey+response.getBody());
            return  ResponseEntity.ok().body(response.getBody());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(response.getBody());
        }
    }

    public ResponseEntity<Object> saveResponse(NafathResponse nafathResponse) {
        if (nafathResponse != null) {
            try {
                Object o = decodeJWT(nafathResponse.getToken());
                nafathResponseRepo.save(nafathResponse);
                nafathPayloadRepo.save(new NafathPayload(nafathResponse.getTransId(), o));
                sendNotification();
                saveDataInMysql(o);
                return ResponseEntity.ok().body("Saved Data SuccessFull");
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().body("No Record Found");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Something went wrong");
            }
        } else {
            return ResponseEntity.badRequest().body("No Record Found");
        }
    }

    private void saveDataInMysql(Object o) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("APP-ID", "eb4ba20f");
        headers.set("APP-KEY", "9bb86fcc9488a1ed8c54185a4dd58005");
        HttpEntity<?> entity = new HttpEntity<>(o, headers);
        String uriTemplate = "https://seulah.com/";

    }

    private void sendNotification() {
    }

    public static String decodeJWT(String jwtToken) {
        StringTokenizer st = new StringTokenizer(jwtToken, ".");
        if (st.countTokens() != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String header = new String(Base64.getUrlDecoder().decode(st.nextToken()));
        String payload = new String(Base64.getUrlDecoder().decode(st.nextToken()));

        return payload;
    }

    public ResponseEntity<Object> getDetailsByNafath() {
        return ResponseEntity.ok().body(nafathResponseRepo.findAll());
    }
}
