package com.seulah.appdesign.apicontroller.nafith.service;

import com.seulah.appdesign.apicontroller.nafith.dto.Sanad;
import com.seulah.appdesign.apicontroller.nafith.dto.SanadGroupDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

import static com.seulah.appdesign.apicontroller.nafith.constants.Constants.*;

@Service
public class NafithService {
    HttpHeaders headers = new HttpHeaders();
    String[] gidValues;
    String[] ridValues;
    String data = "{\"debtor\":{\"national_id\":\"1069282455\"},\"city_of_issuance\":\"1\",\"city_of_payment\":\"1\",\"debtor_phone_number\":\"0546258295\",\"total_value\":1000,\"currency\":\"SAR\",\"max_approve_duration\":6400,\"reference_id\":\"1\",\"country_of_issuance\":\"SA\",\"country_of_payment\":\"SA\",\"sanad\":[{\"due_type\":\"upon request\",\"due_date\":\"2026-12-28\",\"total_value\":1000,\"reference_id\":\"sanad1\"}]}";
    String method = "POST";
    String endpoint = "/api/sanad-group/";
    String sanadObject = "";
    String secretKey = "98fTu4ZrOy9J3fhGlim46hO8o0MyIkPC97lAF8HLgvVIsPBipdpWIU1Cs9kBfYNTzmuTvRSBG6pDkQOL1vPRt1Df1bxAyTy2Iw0ssyDlVKb8UmoK9YaeLEiKeHWkC8nY";

    public String setSanadGroup(String trackingId, String timestamp, String signature, SanadGroupDto sanadGroupDto) {


            try {
                long unixTimestamp = Instant.now().getEpochSecond();
                String encodedData = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
                String message = String.format("%s\n%s\n%s\nid=%s&t=%s&ed=%s",
                        method, "nafith.sa", endpoint, "", "1709036985237", encodedData);
                System.out.println(message);

                System.out.println(hmacSha256(secretKey,message));
                return "";
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
                return "";
            }

    }
    private static String hmacSha256(String secretKey, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        byte[] signedBytes = sha256Hmac.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(signedBytes);
    }

}
