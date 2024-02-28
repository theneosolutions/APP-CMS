package com.seulah.appdesign.apicontroller.nafith.service;

import com.seulah.appdesign.apicontroller.nafith.model.CallBackDetails;
import com.seulah.appdesign.apicontroller.nafith.model.NafithCreateSingleSanad;
import com.seulah.appdesign.apicontroller.nafith.model.SanadDetails;
import com.seulah.appdesign.apicontroller.nafith.repo.CallBackResponse;
import com.seulah.appdesign.apicontroller.nafith.repo.CreateSingleSanadGroupRepo;
import com.seulah.appdesign.apicontroller.nafith.repo.SanadDetailsRepo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class NafithService {

    private final CreateSingleSanadGroupRepo createSingleSanadGroupRepo;
    private final CallBackResponse callBackResponse;
    private final SanadDetailsRepo sanadDetailsRepo;
    String data = "{\"debtor\":{\"national_id\":\"1069282455\"},\"city_of_issuance\":\"1\",\"city_of_payment\":\"1\",\"debtor_phone_number\":\"0546258295\",\"total_value\":1000,\"currency\":\"SAR\",\"max_approve_duration\":6400,\"reference_id\":\"1\",\"country_of_issuance\":\"SA\",\"country_of_payment\":\"SA\",\"sanad\":[{\"due_type\":\"upon request\",\"due_date\":\"2026-12-28\",\"total_value\":1000,\"reference_id\":\"sanad1\"}]}";
    String method = "POST";
    String endpoint = "/api/sanad-group/";

    String secretKey = "98fTu4ZrOy9J3fhGlim46hO8o0MyIkPC97lAF8HLgvVIsPBipdpWIU1Cs9kBfYNTzmuTvRSBG6pDkQOL1vPRt1Df1bxAyTy2Iw0ssyDlVKb8UmoK9YaeLEiKeHWkC8nY";
    private final RestTemplate restTemplate;

    public NafithService(CreateSingleSanadGroupRepo createSingleSanadGroupRepo, CallBackResponse callBackResponse, SanadDetailsRepo sanadDetailsRepo, RestTemplate restTemplate) {
        this.createSingleSanadGroupRepo = createSingleSanadGroupRepo;
        this.callBackResponse = callBackResponse;
        this.sanadDetailsRepo = sanadDetailsRepo;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> setSanadGroup(Object o) {
        try {


            String encodedData = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
            String message = String.format("%s\n%s\n%s\nid=%s&t=%s&ed=%s",
                    method, "nafith.sa", endpoint, "", "1709041904653", encodedData);
           // System.out.println(message);

            //  System.out.println(hmacSha256(secretKey,message));
            System.out.println(o.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("X-Nafith-Timestamp", "1709041904653");
            headers.set("X-Nafith-Tracking-Id", "145");
            headers.set("X-Nafith-Signature", hmacSha256(secretKey, message));
            headers.setBearerAuth("CBdK1VIPET1IpPBeQgOWh9PJTdEuom");
            HttpEntity<?> requestEntity = new HttpEntity<>(o, headers);
            // Build the URI with parameters if needed
            String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/";
            try {
                System.out.println(headers);
                ResponseEntity<Object> response =  restTemplate.postForEntity(apiUrl,requestEntity, Object.class);
                    createSingleSanadGroup(response.getBody());
                return response;
            } catch (RestClientException e) {
                // Handle exception, log it, or return an error response
                e.printStackTrace();
                return new ResponseEntity<>("Error during API call", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during API call", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> createSingleSanadGroup(Object o) {
        if (o != null) {
            createSingleSanadGroupRepo.save(new NafithCreateSingleSanad(o));
            return ResponseEntity.ok().body(o);
        } else {
            return ResponseEntity.badRequest().body(o);
        }
    }

    private static String hmacSha256(String secretKey, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        byte[] signedBytes = sha256Hmac.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(signedBytes);
    }

    public void callBackSanadDetails(Object sanad) {
        callBackResponse.save(new CallBackDetails(sanad));
        System.out.println(sanad);
    }

    public ResponseEntity<?> getSanadDetails(String group_uuid,String sanad_uid) {
        // Setting up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", "t4");
        headers.set("X-Nafith-Timestamp", "1709041904653");
        headers.set("X-Nafith-Signature", "IcP6fLluv1felWN3MgQOnn3zbb6xcHXsd+O2p+Q7yAY=");
        headers.setBearerAuth("CBdK1VIPET1IpPBeQgOWh9PJTdEuom");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        // URL from the curl command
        String url = "https://sandbox.nafith.sa/api/sanad/"+group_uuid+"/"+sanad_uid+"/";

        // Making the GET request
        ResponseEntity<Object> response = restTemplate.exchange(url,HttpMethod.GET, entity,Object.class);


        sanadDetailsRepo.save(new SanadDetails(response.getBody()));
        System.out.println(response.getBody());

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<byte[]> downloadPDF(String uuid) {
        // Setting up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", "T6");
        headers.set("X-Nafith-Timestamp", "1709041904653");
        headers.set("X-Nafith-Signature", "l/XWKm6cUfdPTKhACy2K0KiFsZQKw1V/Gikr42eylDo=");
        headers.set("Authorization", "Bearer CBdK1VIPET1IpPBeQgOWh9PJTdEuom");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // URL from the curl command
        String url = "https://sandbox.nafith.sa/api/sanad-group/download/"+uuid+"/";

        // Making the GET request for file download
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class, entity);

        return ResponseEntity.ok().body(response.getBody());

    }
}
