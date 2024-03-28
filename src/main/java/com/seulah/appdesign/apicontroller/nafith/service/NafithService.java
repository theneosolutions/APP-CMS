package com.seulah.appdesign.apicontroller.nafith.service;

import com.seulah.appdesign.apicontroller.nafith.model.CallBackDetails;
import com.seulah.appdesign.apicontroller.nafith.model.NafithCreateSingleSanad;
import com.seulah.appdesign.apicontroller.nafith.model.SanadDetails;
import com.seulah.appdesign.apicontroller.nafith.repo.CallBackResponse;
import com.seulah.appdesign.apicontroller.nafith.repo.CreateSingleSanadGroupRepo;
import com.seulah.appdesign.apicontroller.nafith.repo.SanadDetailsRepo;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

@Service
public class NafithService {

    private final CreateSingleSanadGroupRepo createSingleSanadGroupRepo;
    private final CallBackResponse callBackResponse;
    private final SanadDetailsRepo sanadDetailsRepo;
    String data = "{\"debtor\":{\"national_id\":\"1069282455\"},\"city_of_issuance\":\"1\",\"city_of_payment\":\"1\",\"debtor_phone_number\":\"0546258295\",\"total_value\":1000,\"currency\":\"SAR\",\"max_approve_duration\":6400,\"reference_id\":\"1\",\"country_of_issuance\":\"SA\",\"country_of_payment\":\"SA\",\"sanad\":[{\"due_type\":\"upon request\",\"due_date\":\"2026-12-28\",\"total_value\":1000,\"reference_id\":\"sanad1\"}]}";
    String method = "POST";
    String endpoint = "/api/sanad-group/";

   // String secretKey = "98fTu4ZrOy9J3fhGlim46hO8o0MyIkPC97lAF8HLgvVIsPBipdpWIU1Cs9kBfYNTzmuTvRSBG6pDkQOL1vPRt1Df1bxAyTy2Iw0ssyDlVKb8UmoK9YaeLEiKeHWkC8nY";
   String secretKey ="zgNyLTBJpIHWqVMJCSFyMIUTRHIgqzpbzMMc8316U59g4wyELuZGX3wMtHCRRr39kBRfiOtHZhAeV8Sl85FTT4RXXLsDMvsAcidE8oNaj16clPynhtitvc2GcqaKJm2i";
    private final RestTemplate restTemplate;

    public NafithService(CreateSingleSanadGroupRepo createSingleSanadGroupRepo, CallBackResponse callBackResponse, SanadDetailsRepo sanadDetailsRepo, RestTemplate restTemplate) {
        this.createSingleSanadGroupRepo = createSingleSanadGroupRepo;
        this.callBackResponse = callBackResponse;
        this.sanadDetailsRepo = sanadDetailsRepo;
        this.restTemplate = restTemplate;
    }
    HashMap<String,String> response = new HashMap<>();
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
            headers.setBearerAuth(getAccessToken());
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
                response.put("message","INTERNAL_SERVER_ERROR");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            response.put("message","INTERNAL_SERVER_ERROR");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (HttpClientErrorException.BadRequest e){
            response.put("message","Error during API call");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    public String getAccessToken(){

        // Setting up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
   //sandBox   //  headers.set("Authorization", "Basic NWZmWG5uSzJ6M2Y1RVYwalo0TFlhOVRCYTNEdkFBVzdhUlR6bjRWQjpPVjdNRnFJNVQ4QmRnMGh3MmpaTW1pSE5ldGpqNjFhWG9BaTZjNE8zSTBWVUJETzJaU0owd1dnSGJkd0ViZjNKYkozTTdqdVRydUI0NDhBS0Fpa0NrU1JoMXhlSERjRXhXOVFwMEhSektLczc1c0VoeVZjM3M2ZE5rTnNJNEdmWQ==");
        headers.set("Authorization","REFacXQ1NFZtREtVcWFoM1BLeXhsMWthd0ZRYmFHVjJXVXNST2V6Rzo0REZuYnQ4ckRXd0locWVaMGdiMU1jZlJBdE1VcnBGWjdsMzJXWmtrRkdVcTV1UDhHWVZkZjVtRVQ3aVZGdm4wbGdQU1UySGNjU2tLNTRYZUZMMjZKbWI4UTN3cjhoeU9Ca01ZcmZ6VmlkN3NBMWVZOWZRM2RCeG41TVU0TGxSUA==");
        // Setting up the request body
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("scope", "read write");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        // URL from the curl command
   //sandbox     String url = "https://sandbox.nafith.sa/api/oauth/token/";
        String url = "https://nafith.sa/api/oauth/token/";
        // Making the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        String accessToken = jsonResponse.getString("access_token");

        return accessToken;
    }

    public ResponseEntity<?> getSanadDetails(String group_uuid,String sanad_uid) {
        // Setting up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", "t4");
        headers.set("X-Nafith-Timestamp", "1709041904653");
        headers.set("X-Nafith-Signature", "IcP6fLluv1felWN3MgQOnn3zbb6xcHXsd+O2p+Q7yAY=");
        headers.setBearerAuth(getAccessToken());
        System.out.println(getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // URL from the curl command
        String url = "https://nafith.sa/api/sanad/"+group_uuid+"/"+sanad_uid+"/";

        // Making the GET request
        ResponseEntity<Object> response = restTemplate.exchange(url,HttpMethod.GET, entity,Object.class);


        sanadDetailsRepo.save(new SanadDetails(response.getBody()));
        System.out.println(response.getBody());

        return ResponseEntity.ok().body(response);
    }
    HashMap<String,String> map = new HashMap<>();
    public ResponseEntity<?> downloadPDF(String uuid) {
        // Setting up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", "T6");
        headers.set("X-Nafith-Timestamp", "1709041904653");
        headers.set("X-Nafith-Signature", "l/XWKm6cUfdPTKhACy2K0KiFsZQKw1V/Gikr42eylDo=");
        headers.setBearerAuth(getAccessToken());
        System.out.println(getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // URL from the curl command
        String url = "https://sandbox.nafith.sa/api/sanad-group/download/"+uuid+"/";

        // Making the GET request for file download
        ResponseEntity<byte[]> response = restTemplate.exchange(url,HttpMethod.GET, entity,byte[].class);
        if (response.getBody() != null && response.getBody().length > 0) {
            // Convert byte array to Base64 encoded string
            String base64EncodedPdf = Base64.getEncoder().encodeToString(response.getBody());
            // base64EncodedPdf now contains your PDF in Base64 encoded form
            System.out.println("Base64 Encoded PDF: " + base64EncodedPdf);

            map.put("Base64",base64EncodedPdf);
            return ResponseEntity.ok().body(map);
        } else {
            return ResponseEntity.badRequest().body("No PDF data received from the server");

        }


    }
}
