package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.DesignScreen;
import com.seulah.appdesign.repository.DesignScreenRepository;
import com.seulah.appdesign.request.DesignScreenRequest;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DesignScreenService {
    private final DesignScreenRepository designScreenRepository;

    public DesignScreenService(DesignScreenRepository designScreenRepository) {
        this.designScreenRepository = designScreenRepository;
    }


    public ResponseEntity<MessageResponse> saveDesignScreen(DesignScreenRequest designRequest) {
        DesignScreen design = new DesignScreen();
        design.setAddScreen(designRequest.getScreen());
        design.setScreens(designRequest.getScreens());
        design = designScreenRepository.save(design);

        return new ResponseEntity<>(new MessageResponse("Successfully Created Design", design, false), HttpStatus.CREATED);
    }


    public ResponseEntity<MessageResponse> getDesignScreenById(String id) {
        Optional<DesignScreen> design = designScreenRepository.findById(id);
        if (design.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", design, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAllDesignScreen() {
        List<DesignScreen> designList = designScreenRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", designList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteDesignScreenById(String id) {
        Optional<DesignScreen> design = designScreenRepository.findById(id);
        if (design.isPresent()) {
            designScreenRepository.delete(design.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateDesignById(String id, DesignScreenRequest designRequest) {
        Optional<DesignScreen> designOptional = designScreenRepository.findById(id);
        if (designOptional.isPresent()) {
            DesignScreen design = designOptional.get();
            if (designRequest.getScreen() != null && !designRequest.getScreen().isEmpty()) {
                design.setAddScreen(designRequest.getScreen());
            }
            if (designRequest.getScreens() != null && !designRequest.getScreens().isEmpty()) {
                design.setScreens(designRequest.getScreens());
            }

            design = designScreenRepository.save(design);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", design, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


}
