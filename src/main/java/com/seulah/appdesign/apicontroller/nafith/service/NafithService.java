package com.seulah.appdesign.apicontroller.nafith.service;

import com.seulah.appdesign.apicontroller.nafith.dto.Sanad;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.seulah.appdesign.apicontroller.nafith.constants.Constants.*;

@Service
public class NafithService {
    HttpHeaders headers = new HttpHeaders();
    String[] gidValues;
    String[] ridValues;
    public void setSanadGroup(String trackingId ,String timestamp,String signature) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/";

        // Replace UUIDs and Strings with your actual values
         gidValues = new String[]{"UUID1", "UUID2", /* ... */};
         ridValues = new String[]{"String1", "String2", /* ... */};

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        for (String gid : gidValues) {
            builder.queryParam("gid", gid);
        }
        for (String rid : ridValues) {
            builder.queryParam("rid", rid);
        }
        String url = builder.build().toUriString();

        // Create HttpHeaders with the required headers
        headers.set(X_Nafith_Tracking_Id, trackingId);
        headers.set(X_Nafith_Timestamp, timestamp);
        headers.set(X_Nafith_Signature, signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);
    }
    public void sanadStatus(String trackingId ,String timestamp,String signature){
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/status/";
         gidValues = new String[]{"UUID1", "UUID2", /* ... */};
         ridValues = new String[]{"String1", "String2", /* ... */};

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        for (String gid : gidValues) {
            builder.queryParam("gid", gid);
        }
        for (String rid : ridValues) {
            builder.queryParam("rid", rid);
        }
        String url = builder.build().toUriString();

        // Create HttpHeaders with the required headers
        headers.set(X_Nafith_Tracking_Id, trackingId);
        headers.set(X_Nafith_Timestamp, timestamp);
        headers.set(X_Nafith_Signature, signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<Sanad> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

    }

    public void sanadGroupListByDebtorId(){
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/by-debtor/<debtor-nin>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <debtor-nin> with the actual debtor's National Identification Number
        String debtorNin = "your-actual-nin";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific debtor's National Identification Number
        String url = apiUrl.replace("<debtor-nin>", debtorNin);

        // Create HttpHeaders with the required headers
        headers.set(X_Nafith_Tracking_Id, trackingId);
        headers.set(X_Nafith_Timestamp, timestamp);
        headers.set(X_Nafith_Signature, signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);

    }
}
