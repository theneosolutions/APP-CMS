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

import static com.seulah.appdesign.apicontroller.nafith.constants.Constants.*;

@Service
public class NafithService {
    HttpHeaders headers = new HttpHeaders();
    String[] gidValues;
    String[] ridValues;

    public String setSanadGroup(String trackingId, String timestamp, String signature, SanadGroupDto sanadGroupDto) {
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
        return responseEntity.getBody();
    }

    public String sanadStatus(String trackingId, String timestamp, String signature) {
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
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);
        return responseEntity.getBody();
    }

    public String sanadGroupListByDebtorId(String id) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/by-debtor/<debtor-nin>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <debtor-nin> with the actual debtor's National Identification Number
        String debtorNin = id;

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

        return responseEntity.getBody();

    }

    public String sanadGroupDetails(String sanadGroupId) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/<sanad-group-uuid>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <sanad-group-uuid> with the actual UUID
        String sanadGroupUuid = "your-actual-uuid";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid
        String url = apiUrl.replace("<sanad-group-uuid>", sanadGroupUuid);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();

        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String sanadGroupDetailsWithCreditCheck(String sanadGroupId) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/<sanad-group-uuid>/<creditor-nin>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <sanad-group-uuid> and <creditor-nin> with the actual UUID and National Identification Number
        String sanadGroupUuid = "your-actual-uuid";
        String creditorNin = "creditor-actual-nin";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid and creditor-nin
        String url = apiUrl
                .replace("<sanad-group-uuid>", sanadGroupUuid)
                .replace("<creditor-nin>", creditorNin);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String sanadGroupDetailsWithReferenceId(String Id) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/by-reference-id/<reference_id>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <reference_id> with the actual reference ID
        String referenceId = Id;

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific reference ID
        String url = apiUrl.replace("<reference_id>", referenceId);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public byte[] downloadPdfSanadGroup(String sanadGroupId) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/download/<sanad-group-uuid>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <sanad-group-uuid> with the actual UUID
        String sanadGroupUuid = "your-actual-uuid";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid
        String url = apiUrl.replace("<sanad-group-uuid>", sanadGroupUuid);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<byte[]> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null, byte[].class);

        // Process the response as needed
        byte[] responseBody = responseEntity.getBody();
        // You can save or process the byte array as needed
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String sanadGroupDetailsByIdAndSanadId(String sanadGroupId, String sanadId) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad/<sanad-group-uuid>/<sanad-uuid>";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <sanad-group-uuid> and <sanad-uuid> with the actual UUIDs
        String sanadGroupUuid = "your-actual-sanad-group-uuid";
        String sanadUuid = "your-actual-sanad-uuid";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid and sanad-uuid
        String url = apiUrl
                .replace("<sanad-group-uuid>", sanadGroupUuid)
                .replace("<sanad-uuid>", sanadUuid);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String sanadDetailsByReferenceId(String id) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad/by-reference-id/<reference-id>";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <reference-id> with the actual reference ID
        String referenceId = id;

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific reference ID
        String url = apiUrl.replace("<reference-id>", referenceId);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String sanadDetailsByNumber() {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad/by-number/<sanad-number>/<creditor-nin>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace <sanad-number> and <creditor-nin> with the actual sanad number and creditor's National Identification Number
        String sanadNumber = "your-actual-sanad-number";
        String creditorNin = "your-actual-creditor-nin";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad number and creditor's National Identification Number
        String url = apiUrl
                .replace("<sanad-number>", sanadNumber)
                .replace("<creditor-nin>", creditorNin);

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, null);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String resendPendingSms(String sanadGroupId) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/resend-pending-sms/<sanad-group-uuid>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace placeholders in the JSON object with actual values
        String sanadGroupJsonObject = "{...}";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid
        String url = apiUrl.replace("<sanad-group-uuid>", "your-actual-sanad-group-uuid");

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers and request method
        RequestCallback requestCallback = request -> {
            request.getHeaders().addAll(headers);
        };


        // Create the request entity with the JSON object and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(sanadGroupJsonObject, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }

    public String updateSanadGroupById(String sanadGroupId, SanadGroupDto updatedSanadGroupDto) {
        // Replace placeholders with your actual values
        String apiUrl = "https://sandbox.nafith.sa/api/sanad-group/<sanad-group-uuid>/";
        String trackingId = "<TRACKING_ID>";
        String timestamp = "<TIMESTAMP>";
        String signature = "<SIGNATURE>";

        // Replace placeholders in the JSON object with actual values
        String sanadGroupJsonObject = "{...}";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with the specific sanad-group-uuid
        String url = apiUrl.replace("<sanad-group-uuid>", "your-actual-sanad-group-uuid");

        // Create HttpHeaders with the required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-Nafith-Tracking-Id", trackingId);
        headers.set("X-Nafith-Timestamp", timestamp);
        headers.set("X-Nafith-Signature", signature);

        // Create a RequestCallback to set the headers and request method
        RequestCallback requestCallback = request -> {
            request.getHeaders().addAll(headers);
        };

        // Create the request entity with the JSON object and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(sanadGroupJsonObject, headers);

        // Make the PATCH request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, String.class);

        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response: " + responseBody);
        return responseEntity.getBody();
    }
}
