package com.seulah.appdesign.apicontroller.selaaapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulah.appdesign.apicontroller.selaaapi.dto.TopUpWalletAmount;
import com.seulah.appdesign.apicontroller.selaaapi.dto.WalletBalance;
import com.seulah.appdesign.apicontroller.selaaapi.repo.WalletGetBalanceRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    public WalletService(RestTemplateBuilder restTemplateBuilder, WalletGetBalanceRepository walletGetBalanceRepository) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication("b2a46063-5a53-41aa-aac5-b10439b4ffc0", "4fc58273-3169-4073-a2a6-21f54c397ab3")
                .build();
        this.walletGetBalanceRepository = walletGetBalanceRepository;
    }

    public ResponseEntity<?> getWalletBalance() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
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

    public ResponseEntity<?> topupWalletAmount(TopUpWalletAmount obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
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
