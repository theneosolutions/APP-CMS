package com.seulah.appdesign.apicontroller.selaaapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulah.appdesign.apicontroller.selaaapi.dto.ApiResponse;
import com.seulah.appdesign.apicontroller.selaaapi.dto.OperationsBuyResponse;
import com.seulah.appdesign.apicontroller.selaaapi.dto.OperationsTransferResponse;
import com.seulah.appdesign.apicontroller.selaaapi.repo.OperationBuyRepository;
import com.seulah.appdesign.apicontroller.selaaapi.repo.OperationTransferRepository;
import com.seulah.appdesign.apicontroller.selaaapi.request.OperationsBuyRequest;
import com.seulah.appdesign.apicontroller.selaaapi.request.OperationsTransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
@Slf4j
public class OperationsBuyService {
    private final RestTemplate restTemplate;
    private final OperationBuyRepository operationBuyRepository;
    private final OperationTransferRepository operationsTransferRepository;

    public OperationsBuyService(RestTemplate restTemplate, OperationBuyRepository operationBuyRepository, OperationTransferRepository operationsTransferRepository) {
        this.restTemplate = restTemplate;
        this.operationBuyRepository = operationBuyRepository;
        this.operationsTransferRepository = operationsTransferRepository;
    }

    public ResponseEntity<?> getBuyDataFromApi(OperationsBuyRequest operationsBuyRequest) {
        // Set up headers with the necessary authentication information
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Authorization", "Basic YjJhNDYwNjMtNWE1My00MWFhLWFhYzUtYjEwNDM5YjRmZmMwOjRmYzU4MjczLTMxNjktNDA3My1hMmE2LTIxZjU0YzM5N2FiMw==");

        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/operations/buy";
        HttpEntity<OperationsBuyRequest> requestEntity = new HttpEntity<>(operationsBuyRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OperationsBuyResponse responseObject = objectMapper.readValue(response.getBody(), OperationsBuyResponse.class);

            saveBuyDataToDatabase(responseObject);

            OperationsBuyResponse apiResponse = new OperationsBuyResponse(responseObject.getOwnershipFileUrl(), responseObject.getCertificates(), responseObject.getOwnershipId()
                    , "200", "true", "false", "Record has been saved");


            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Something went wrong!");
    }

    private void saveBuyDataToDatabase(OperationsBuyResponse responseObject) {
        operationBuyRepository.save(responseObject);
    }

    public ResponseEntity<?> getTransferDataFromApi(OperationsTransferRequest operationsTransferRequest) {
        // Set up headers with the necessary authentication information
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Authorization", "Basic YjJhNDYwNjMtNWE1My00MWFhLWFhYzUtYjEwNDM5YjRmZmMwOjRmYzU4MjczLTMxNjktNDA3My1hMmE2LTIxZjU0YzM5N2FiMw==");

        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/operations/transfer";
        ResponseEntity<String> response = null;
        HttpEntity<OperationsTransferRequest> requestEntity = new HttpEntity<>(operationsTransferRequest, headers);
        try {
            response = restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OperationsTransferResponse responseObject = objectMapper.readValue(response != null && response.getBody() != null ? response.getBody() : "", OperationsTransferResponse.class);

            saveTransferDataToDatabase(responseObject);

            OperationsTransferResponse apiResponse = new OperationsTransferResponse(responseObject.getOwnershipFileUrl(), responseObject.getOwnershipId(), responseObject.getCertificates()
                    , "200", "true", "false", "Record has been saved");

            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }

    private void saveTransferDataToDatabase(OperationsTransferResponse responseObject) {
        operationsTransferRepository.save(responseObject);
    }


    public ResponseEntity<?> getRedeemDataFromApi(String ownershipId) {
        // Set up headers with the necessary authentication information
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Authorization", "Basic YjJhNDYwNjMtNWE1My00MWFhLWFhYzUtYjEwNDM5YjRmZmMwOjRmYzU4MjczLTMxNjktNDA3My1hMmE2LTIxZjU0YzM5N2FiMw==");

        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/operations/redeem";
        HttpEntity<String> requestEntity = new HttpEntity<>(ownershipId, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        try {
            if (!Objects.requireNonNull(response.getBody()).isEmpty()) {
                ApiResponse apiResponse = new ApiResponse("200", "true", "false", "Record has been saved");
                return ResponseEntity.ok().body(apiResponse);
            }
            ApiResponse apiResponse = new ApiResponse("403", "false", "true", "No transaction found for this record");

            return ResponseEntity.badRequest().body(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }

}