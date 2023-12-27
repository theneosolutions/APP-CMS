package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.AppFlow;
import com.seulah.appdesign.repository.AppFlowRepository;
import com.seulah.appdesign.request.AppFlowRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AppFlowService {
    private final AppFlowRepository appFlowRepository;

    public AppFlowService(AppFlowRepository appFlowRepository) {
        this.appFlowRepository = appFlowRepository;
    }

    public ResponseEntity<MessageResponse> saveAppFlow(AppFlowRequest appFlowRequest) {
        AppFlow appFlow = new AppFlow();

        appFlow.setApiFlow(appFlowRequest.getApiFlow());
        appFlow.setScreenFlow(appFlowRequest.getScreenFlow());
        appFlow.setApiResponse(appFlowRequest.getApiResponse());
        appFlow = appFlowRepository.save(appFlow);
        log.info("Saved api flow successfully {}", appFlow);
        return new ResponseEntity<>(new MessageResponse("Successfully Created App Flow", appFlow, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getAppFlowById(String id) {
        Optional<AppFlow> appFlow = appFlowRepository.findById(id);
        return appFlow.map(flow -> new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, flow, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<AppFlow> appFlowList = appFlowRepository.findAll();
        log.info("Getting all api flow");
        return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, appFlowList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<AppFlow> appFlow = appFlowRepository.findById(id);
        return appFlow.map(flow -> new ResponseEntity<>(new MessageResponse("Success", flow, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> updateById(String id, AppFlowRequest appFlowRequest) {
        Optional<AppFlow> appFlowOptional = appFlowRepository.findById(id);
        if (appFlowOptional.isPresent()) {
            if (appFlowRequest.getApiFlow() != null && !appFlowRequest.getApiFlow().isEmpty()) {
                appFlowOptional.get().setApiFlow(appFlowRequest.getApiFlow());
            }
            if (appFlowRequest.getApiResponse() != null && !appFlowRequest.getApiResponse().isEmpty()) {
                appFlowOptional.get().setApiResponse(appFlowRequest.getApiResponse());
            }
            if (appFlowRequest.getScreenFlow() != null && !appFlowRequest.getScreenFlow().isEmpty()) {
                appFlowOptional.get().setScreenFlow(appFlowRequest.getScreenFlow());
            }
            log.info("Api flow updated successfully {}", appFlowOptional);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", appFlowRepository.save(appFlowOptional.get()), false), HttpStatus.OK);
        }
        log.info("Data not found against the id {}", id);
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }
}
