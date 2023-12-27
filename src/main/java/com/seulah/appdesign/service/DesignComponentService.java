package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.DesignComponent;
import com.seulah.appdesign.entity.DesignScreen;
import com.seulah.appdesign.repository.DesignComponentRepository;
import com.seulah.appdesign.repository.DesignScreenRepository;
import com.seulah.appdesign.request.DesignComponentRequest;
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
public class DesignComponentService {
    private final DesignComponentRepository designComponentRepository;

    private final DesignScreenRepository designScreenRepository;

    public DesignComponentService(DesignComponentRepository designComponentRepository, DesignScreenRepository designScreenRepository) {
        this.designComponentRepository = designComponentRepository;
        this.designScreenRepository = designScreenRepository;
    }

    public ResponseEntity<MessageResponse> saveDesignComponent(DesignComponentRequest designComponentRequest) {
        DesignComponent designComponent = new DesignComponent();
        designComponent.setComponents(designComponentRequest.getComponents());
        designComponent.setSizeRedisCard(designComponentRequest.getSizeRedisCard());
        designComponent = designComponentRepository.save(designComponent);
        log.info("Created design component successfully {}", designComponent);
        return new ResponseEntity<>(new MessageResponse("Successfully Created Design", designComponent, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getDesignComponentById(String id) {
        Optional<DesignComponent> designComponent = designComponentRepository.findById(id);
        return designComponent.map(design -> new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, design, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAllDesignComponent() {
        List<DesignComponent> designComponentList = designComponentRepository.findAll();
        return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, designComponentList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteDesignComponentById(String id) {
        Optional<DesignComponent> designComponentOptional = designComponentRepository.findById(id);

        if (designComponentOptional.isPresent()) {
            DesignComponent designComponent = designComponentOptional.get();

            List<DesignScreen> screensToUpdate = designScreenRepository.findByDesignComponentListContaining(designComponent);
            screensToUpdate.forEach(screen -> {
                screen.getDesignComponentList().remove(designComponent);
                designScreenRepository.save(screen);
            });
            designComponentRepository.delete(designComponent);
            log.info("Delete design component successfully");
            return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, null, false), HttpStatus.OK);
        }
        log.info("No record found against this id: {}", id);
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> updateDesignComponentById(String id, DesignComponentRequest designComponentRequest) {
        Optional<DesignComponent> designComponentOptional = designComponentRepository.findById(id);
        if (designComponentOptional.isPresent()) {
            if (designComponentRequest.getComponents() != null && !designComponentRequest.getComponents().isEmpty()) {
                designComponentOptional.get().setComponents(designComponentRequest.getComponents());
            }
            if (designComponentRequest.getSizeRedisCard() != null && !designComponentRequest.getSizeRedisCard().isEmpty()) {
                designComponentOptional.get().setSizeRedisCard(designComponentRequest.getSizeRedisCard());
            }
            log.info("Update design component successfully {}", designComponentOptional);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", designComponentRepository.save(designComponentOptional.get()), false), HttpStatus.OK);
        }
        log.info("No record found against this id :{}", id);
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.OK);
    }


}
