package com.seulah.appdesign.apicontroller.oursms.service;

import com.seulah.appdesign.apicontroller.oursms.dto.SmsRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OursmsService {
    private final RestTemplate restTemplate;
    SmsRequest smsRequest;
    private final String apiUrl = "https://api.oursms.com/msgs/sms";
    private final String bearerToken = "Bearer fgih_GLbk32-AUv6Ucqn"; // Replace with your actual bearer token

    public OursmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String ourSms(String mobileNumber,String otp){
        smsRequest = new SmsRequest();
        smsRequest.setSrc("SeulahFin");;
        smsRequest.setDests(new String[]{mobileNumber, mobileNumber});
        smsRequest.setBody(otp);
        smsRequest.setPriority(0);
        smsRequest.setDelay(0);
        smsRequest.setValidity(0);
        smsRequest.setMaxParts(0);
        smsRequest.setDlr(0);
        smsRequest.setPrevDups(0);
        smsRequest.setMsgClass("");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SmsRequest> requestEntity = new HttpEntity<>(smsRequest,headers);
      ResponseEntity<String> response = restTemplate.exchange(apiUrl,
              HttpMethod.POST, requestEntity, String.class);
        if(response.getBody()!=null){
            return "true";
        }
        return "false";
    }
}
