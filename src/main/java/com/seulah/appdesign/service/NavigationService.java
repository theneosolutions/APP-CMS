package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Navigations;
import com.seulah.appdesign.repository.NavigationRepository;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.request.NavigationsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.seulah.appdesign.utils.Constants.SUCCESS;

@Service
@Slf4j
public class NavigationService {
    private final NavigationRepository navigationRepository;

    public NavigationService(NavigationRepository navigationRepository) {
        this.navigationRepository = navigationRepository;
    }

    public ResponseEntity<MessageResponse> saveNavigation(NavigationsRequest navigationsRequest) {
        Navigations navigations = new Navigations();

        navigations.setNavbar(navigationsRequest.getNavbar());
        navigations.setBottomTab(navigationsRequest.getBottomTab());
        navigations.setDrawer(navigationsRequest.getDrawer());
        navigations = navigationRepository.save(navigations);
        log.info("Saved navigation successfully,{}", navigations);
        return new ResponseEntity<>(new MessageResponse("Successfully Created Navigation", navigations, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getDesignById(String id) {
        Optional<Navigations> navigations = navigationRepository.findById(id);
        return navigations.map(navigation -> new ResponseEntity<>(new MessageResponse(SUCCESS, navigation, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Navigations> navigationsList = navigationRepository.findAll();
        log.info("Get All navigation successfully");
        return new ResponseEntity<>(new MessageResponse(SUCCESS, navigationsList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Navigations> navigations = navigationRepository.findById(id);
        if (navigations.isPresent()) {
            navigationRepository.delete(navigations.get());
            log.info("Delete navigation successfully");
            return new ResponseEntity<>(new MessageResponse(SUCCESS, null, false), HttpStatus.OK);
        }
        log.info("No record found against this id {}", id);
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, NavigationsRequest navigationsRequest) {

        Optional<Navigations> navigationsOptional = navigationRepository.findById(id);
        if (navigationsOptional.isPresent()) {
            if (navigationsRequest.getNavbar() != null && !navigationsRequest.getNavbar().isEmpty()) {
                navigationsOptional.get().setNavbar(navigationsRequest.getNavbar());
            }
            if (navigationsRequest.getBottomTab() != null && !navigationsRequest.getBottomTab().isEmpty()) {
                navigationsOptional.get().setBottomTab(navigationsRequest.getBottomTab());
            }
            if (navigationsRequest.getDrawer() != null && !navigationsRequest.getDrawer().isEmpty()) {
                navigationsOptional.get().setDrawer(navigationsRequest.getDrawer());
            }
            log.info("Navigation updated successfully {}", navigationsOptional);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", navigationRepository.save(navigationsOptional.get()), false), HttpStatus.OK);
        }
        log.info("Record does not exist in the database");
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }
}
