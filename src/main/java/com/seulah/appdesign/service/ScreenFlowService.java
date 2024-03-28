package com.seulah.appdesign.service;

import com.seulah.appdesign.dto.ScreenDto;
import com.seulah.appdesign.entity.ScreenFlow;
import com.seulah.appdesign.repository.ScreenFlowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Muhammad Mansoor
 */
@Service
public class ScreenFlowService {
    private final ScreenFlowRepository appFlowRepository;
    Map<String ,String> response = new HashMap<>();

    public ScreenFlowService(ScreenFlowRepository appFlowRepository) {
        this.appFlowRepository = appFlowRepository;
    }

    public ResponseEntity<?> saveAppFlow(List<ScreenDto> screenDto, String brandId){
        ScreenFlow appFlow = appFlowRepository.findByBrandId(brandId);
        if(appFlow!=null){
            appFlow.setBrandId(brandId);
            appFlow.setScreenFlow(screenDto);
            appFlowRepository.save(appFlow);
            response.put("message","Record updated Success Fully!");
            return ResponseEntity.ok(response);
        }else {
            ScreenFlow apiFlow = new ScreenFlow();
            apiFlow.setBrandId(brandId);
            apiFlow.setScreenFlow(screenDto);
            appFlowRepository.save(apiFlow);
            response.put("message","New Record has been Saved !");
            return ResponseEntity.ok(response);
        }
    }
    public ResponseEntity<?> updateAppFlow(List<ScreenDto> screenDto,String brandId){;
        ScreenFlow appFlow = appFlowRepository.findByBrandId(brandId);
        if(appFlow!=null){
            appFlow.setId(appFlow.getId());
            appFlow.setBrandId(brandId);
            appFlow.setScreenFlow(screenDto);
            appFlowRepository.save(appFlow);
            response.put("message","Record updated Success Fully!");
            return ResponseEntity.ok(response);
        }else {
            response.put("message","No Record Found !");
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<?> updateAppFlowByName(List<ScreenDto> screenDto,String brandId){;
        ScreenFlow appFlow = appFlowRepository.findByBrandId(brandId);
        if(appFlow!=null){
            appFlow.setId(appFlow.getId());
            appFlow.setBrandId(brandId);
            appFlow.setScreenFlow(screenDto);
            appFlowRepository.save(appFlow);
            response.put("message","Record updated Success Fully!");
            return ResponseEntity.ok(response);
        }else {
            response.put("message","No Record Found !");
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Map<String ,ScreenFlow>> getAppFlow(String brandId) {
        Map<String ,ScreenFlow> appResponse = new HashMap<>();

        ScreenFlow appFlow = appFlowRepository.findByBrandId(brandId);
        if(appFlow!=null){
            appResponse.put("appFlow",appFlow);
            return ResponseEntity.ok().body(appResponse);
        }else {
            appResponse.put("message",null);
            return ResponseEntity.ok().body(appResponse);
        }
    }
}
