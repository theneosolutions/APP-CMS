package com.seulah.appdesign.apicontroller.selaaapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulah.appdesign.apicontroller.selaaapi.dto.DefaultHistoryMain;
import com.seulah.appdesign.apicontroller.selaaapi.repo.DefaultHistoryRepository;
import com.seulah.appdesign.apicontroller.selaaapi.repo.DefaultTransactionRepository;
import com.seulah.appdesign.apicontroller.selaaapi.dto.DefaultTransaction;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DefaultService {
    private final RestTemplate restTemplate;
    private final DefaultTransactionRepository defaultTransactionReposiotry;
    private final DefaultHistoryRepository defaultHistoryMain;

    public DefaultService(RestTemplateBuilder restTemplateBuilder, DefaultTransactionRepository defaultTransactionReposiotry, DefaultHistoryRepository defaultHistoryMain) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication("b2a46063-5a53-41aa-aac5-b10439b4ffc0", "4fc58273-3169-4073-a2a6-21f54c397ab3")
                .build();
        this.defaultTransactionReposiotry = defaultTransactionReposiotry;
        this.defaultHistoryMain = defaultHistoryMain;
    }

    public ResponseEntity<?> getAllTransactions() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/transactions";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, String.class);
        try {
            saveTransactionToDatabase(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong !");
        }

    }

    private void saveTransactionToDatabase(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DefaultTransaction> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<List<DefaultTransaction>>() {});
        defaultTransactionReposiotry.saveAll(responseObject);
    }

    public ResponseEntity<?> getAllHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/history";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, String.class);
        try {
            saveHistoryToDatabase(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong !");
        }
    }

    private void saveHistoryToDatabase(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<DefaultHistoryMain> responseList = objectMapper.readValue(response.getBody(), new TypeReference<List<DefaultHistoryMain>>() {});
            defaultHistoryMain.saveAll(responseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
