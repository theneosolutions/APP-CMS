package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.DesignComponent;
import com.seulah.appdesign.entity.DesignScreen;
import com.seulah.appdesign.repository.DesignComponentRepository;
import com.seulah.appdesign.repository.DesignScreenRepository;
import com.seulah.appdesign.request.DesignScreenRequest;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.seulah.appdesign.utils.Constants.SUCCESSFULLY_UPDATED;


@Service
@Slf4j
public class DesignScreenService {
    private final DesignScreenRepository designScreenRepository;

    private final DesignComponentRepository designComponentRepository;


    public DesignScreenService(DesignScreenRepository designScreenRepository, DesignComponentRepository designComponentRepository) {
        this.designScreenRepository = designScreenRepository;
        this.designComponentRepository = designComponentRepository;
    }


    public ResponseEntity<MessageResponse> saveDesignScreen(DesignScreenRequest designRequest) {
        DesignScreen design = new DesignScreen();
        design.setApplicationName(designRequest.getApplicationName());
        design.setScreenList(designRequest.getScreenList());
        design = designScreenRepository.save(design);
        log.info("Saved Design screen successfully {}", design);
        return new ResponseEntity<>(new MessageResponse("Successfully Created Design", design, false), HttpStatus.CREATED);
    }


    public ResponseEntity<MessageResponse> getDesignScreenById(String id) {
        Optional<DesignScreen> design = designScreenRepository.findById(id);
        return design.map(designScreen -> new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, designScreen, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAllDesignScreen() {
        List<DesignScreen> designList = designScreenRepository.findAll();
        log.info("Get all design screen");
        return new ResponseEntity<>(new MessageResponse(Constants.SUCCESS, designList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteDesignScreenById(String id) {
        Optional<DesignScreen> design = designScreenRepository.findById(id);
        if (design.isPresent()) {
            designScreenRepository.delete(design.get());
            log.info("Delete design screen successfully");
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }
        log.info("No record found against this id :{}", id);
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateDesignById(String id, DesignScreenRequest designRequest) {
        Optional<DesignScreen> designOptional = designScreenRepository.findById(id);
        if (designOptional.isPresent()) {
            DesignScreen design = designOptional.get();
            if (designRequest.getApplicationName() != null && !designRequest.getApplicationName().isEmpty()) {
                design.setApplicationName(designRequest.getApplicationName());
            }
            if (designRequest.getScreenList() != null && !designRequest.getScreenList().isEmpty()) {
                design.setScreenList(designRequest.getScreenList());
            }

            design = designScreenRepository.save(design);
            log.info("Design screen updated successfully {}", design);
            return new ResponseEntity<>(new MessageResponse(SUCCESSFULLY_UPDATED, design, false), HttpStatus.OK);
        }
        log.info("No record against this Id: {}", id);
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> addComponentInScreen(String screenId, String componentId) {
        Optional<DesignComponent> designComponent = designComponentRepository.findById(componentId);
        Optional<DesignScreen> designScreen = designScreenRepository.findById(screenId);
        if (designScreen.isPresent() && designComponent.isPresent()) {
            if (designScreen.get().getDesignComponentList() == null || designScreen.get().getDesignComponentList().isEmpty()) {
                designScreen.get().setDesignComponentList(new ArrayList<>());
            }
            designScreen.get().getDesignComponentList().add(designComponent.get());
            designScreenRepository.save(designScreen.get());
            log.info("Added component in screen successfully");
            return new ResponseEntity<>(new MessageResponse(SUCCESSFULLY_UPDATED, designScreen, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid ScreenId or Component Id", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteComponentFromScreen(String screenId, String componentId) {
        Optional<DesignScreen> designScreenOptional = designScreenRepository.findById(screenId);
        if (designScreenOptional.isPresent()) {
            DesignScreen designScreen = designScreenOptional.get();

            List<DesignComponent> designComponentList = designScreen.getDesignComponentList();
            if (designComponentList != null && !designComponentList.isEmpty()) {
                designComponentList.removeIf(component -> component.getId().equals(componentId));
                designScreenRepository.save(designScreen);
                log.info("Delete component from screen successfully");
                return new ResponseEntity<>(new MessageResponse(SUCCESSFULLY_UPDATED, designScreen, false), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("No components in the screen to delete", null, false), HttpStatus.OK);

        }
        return new ResponseEntity<>(new MessageResponse("Invalid ScreenId", null, false), HttpStatus.OK);

    }

}
