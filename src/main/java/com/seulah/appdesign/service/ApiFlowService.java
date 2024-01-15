package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.ScreenFlow;
import com.seulah.appdesign.dto.ScreenDto;
import com.seulah.appdesign.entity.ApiFlow;
import com.seulah.appdesign.repository.ApiFlowRepository;
import com.seulah.appdesign.repository.ScreenFlowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiFlowService {
    private final ApiFlowRepository apiFlowRepository;
    private final ScreenFlowRepository appFlowRepository;
    Map<String ,String> response = new HashMap<>();

    public ApiFlowService(ApiFlowRepository apiFlowRepository, ScreenFlowRepository appFlowRepository) {
        this.apiFlowRepository = apiFlowRepository;
        this.appFlowRepository = appFlowRepository;
    }

    public ResponseEntity<?> saveApiFlow(String brandId,Object object){
         ApiFlow flow =   apiFlowRepository.findByBrandId(brandId);
         if(flow!=null){
             flow.setId(flow.getId());
             flow.setBrandId(brandId);
             flow.setFlow(object);
             apiFlowRepository.save(flow);
             return ResponseEntity.ok("Updated Record!");
         }
        ApiFlow apiFlow = new ApiFlow();
        apiFlow.setBrandId(brandId);
        apiFlow.setFlow(object);
        apiFlowRepository.save(apiFlow);
        return ResponseEntity.ok("New Record has been Saved !");
    }

    public ResponseEntity<?> saveAppFlow(List<ScreenDto> screenDto,String brandId){

        System.out.println("screenDto"+screenDto.get(0));
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


    public ResponseEntity<?> getAppFlow(String brandId) {
        Map<String ,ScreenFlow> appResponse = new HashMap<>();

        ScreenFlow appFlow = appFlowRepository.findByBrandId(brandId);
        if(appFlow!=null){
            appResponse.put("appFlow",appFlow);
            return ResponseEntity.ok(appResponse);
        }else{
            appResponse.put("appFlow",null);
            return ResponseEntity.ok(appResponse);
        }
    }
}
