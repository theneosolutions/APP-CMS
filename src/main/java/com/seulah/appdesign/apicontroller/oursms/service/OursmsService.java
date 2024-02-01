package com.seulah.appdesign.apicontroller.oursms.service;

import com.seulah.appdesign.apicontroller.oursms.dto.SmsRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OursmsService {
    private final RestTemplate restTemplate;
    SmsRequest smsRequest;

    public OursmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String ourSms(String idNumber){
        String[] destsArray = {idNumber, idNumber};
        smsRequest = new SmsRequest();
        smsRequest.setSrc("");;
        smsRequest.setDests(destsArray);
        smsRequest.setBody("Arham");
        smsRequest.setPriority(0);
        smsRequest.setDelay(0);
        smsRequest.setValidity(0);
        smsRequest.setMaxParts(0);
        smsRequest.setDlr(0);
        smsRequest.setPrevDups(0);
        smsRequest.setMsgClass("");
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        HttpEntity<SmsRequest> requestEntity = new HttpEntity<>(smsRequest,headers);
      ResponseEntity<String> response = restTemplate.exchange(
                "https://api.oursms.com/msgs/sms", HttpMethod.POST, requestEntity, String.class);
        if(response.getBody()!=null){
            return "true";
        }
        return "false";
    }
}
