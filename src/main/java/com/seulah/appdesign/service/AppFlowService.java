package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.AppFlow;
import com.seulah.appdesign.repository.AppFlowRepository;
import com.seulah.appdesign.request.AppFlowRequest;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

        return new ResponseEntity<>(new MessageResponse("Successfully Created App Flow", appFlow, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getAppFlowById(String id) {
        Optional<AppFlow> appFlow = appFlowRepository.findById(id);
        if (appFlow.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", appFlow, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<AppFlow> appFlowList = appFlowRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", appFlowList, false), HttpStatus.OK);
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

            return new ResponseEntity<>(new MessageResponse("Successfully Updated", appFlowRepository.save(appFlowOptional.get()), false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }
}
