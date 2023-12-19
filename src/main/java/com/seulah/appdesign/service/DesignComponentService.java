package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;


@Service
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

        return new ResponseEntity<>(new MessageResponse("Successfully Created Design", designComponent, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getDesignComponentById(String id) {
        Optional<DesignComponent> designComponent = designComponentRepository.findById(id);
        return designComponent.map(design -> new ResponseEntity<>(new MessageResponse("Success", design, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAllDesignComponent() {
        List<DesignComponent> designComponentList = designComponentRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", designComponentList, false), HttpStatus.OK);
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
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

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
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", designComponentRepository.save(designComponentOptional.get()), false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No record found against this id", null, false), HttpStatus.OK);
    }


}
