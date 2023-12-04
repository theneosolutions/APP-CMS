package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.DesignComponent;
import com.seulah.appdesign.repository.DesignComponentRepository;
import com.seulah.appdesign.request.DesignComponentRequest;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DesignComponentService {
    private final DesignComponentRepository designComponentRepository;

    public DesignComponentService(DesignComponentRepository designComponentRepository) {
        this.designComponentRepository = designComponentRepository;
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
        if (designComponent.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", designComponent, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAllDesignComponent() {
        List<DesignComponent> designComponentList = designComponentRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", designComponentList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteDesignComponentById(String id) {
        Optional<DesignComponent> designComponent = designComponentRepository.findById(id);
        if (designComponent.isPresent()) {
            designComponentRepository.delete(designComponent.get());
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
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


}
