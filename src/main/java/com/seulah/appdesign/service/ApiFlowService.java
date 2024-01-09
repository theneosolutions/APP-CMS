package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.ApiFlow;
import com.seulah.appdesign.repository.ApiFlowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ApiFlowService {
    private final ApiFlowRepository apiFlowRepository;

    public ApiFlowService(ApiFlowRepository apiFlowRepository) {
        this.apiFlowRepository = apiFlowRepository;
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
        return ResponseEntity.ok("Success Fully Saved !");
    }
}
