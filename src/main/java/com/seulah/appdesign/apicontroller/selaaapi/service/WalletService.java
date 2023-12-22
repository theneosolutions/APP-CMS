package com.seulah.appdesign.apicontroller.selaaapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulah.appdesign.apicontroller.selaaapi.dto.WalletBalance;
import com.seulah.appdesign.apicontroller.selaaapi.repo.WalletGetBalanceRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WalletService {
    private final RestTemplate restTemplate;
    private final WalletGetBalanceRepository walletGetBalanceRepository;

    public WalletService(RestTemplate restTemplate, WalletGetBalanceRepository walletGetBalanceRepository) {
        this.restTemplate = restTemplate;
        this.walletGetBalanceRepository = walletGetBalanceRepository;
    }

    public ResponseEntity<?> getWalletBalance() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("Authorization", "Basic YjJhNDYwNjMtNWE1My00MWFhLWFhYzUtYjEwNDM5YjRmZmMwOjRmYzU4MjczLTMxNjktNDA3My1hMmE2LTIxZjU0YzM5N2FiMw==");

            // Build the URI with parameters if needed
            String url = "https://devapi.selaa.sa/wallets/getWalletBalance";
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, requestEntity, String.class);
            try {
                saveWalletBalanceToDatabase(response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Something went wrong !");
            }
    }

    private void saveWalletBalanceToDatabase(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        WalletBalance responseObject = objectMapper.readValue(response.getBody(),  WalletBalance.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        responseObject.setDateTime(formatter.format(date));
        walletGetBalanceRepository.save(responseObject);
    }

    public ResponseEntity<?> topupWalletAmount(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Authorization", "Basic YjJhNDYwNjMtNWE1My00MWFhLWFhYzUtYjEwNDM5YjRmZmMwOjRmYzU4MjczLTMxNjktNDA3My1hMmE2LTIxZjU0YzM5N2FiMw==");

        // Build the URI with parameters if needed
        String url = "https://devapi.selaa.sa/wallets/topupWalletAmount";
        HttpEntity<Object> requestEntity = new HttpEntity<>(obj,headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);
        try {
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong !");
        }
    }
}
