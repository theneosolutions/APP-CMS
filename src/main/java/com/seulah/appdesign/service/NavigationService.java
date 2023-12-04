package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Navigations;
import com.seulah.appdesign.repository.NavigationRepository;
import com.seulah.appdesign.request.MessageResponse;
import com.seulah.appdesign.request.NavigationsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

        return new ResponseEntity<>(new MessageResponse("Successfully Created Navigation", navigations, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getDesignById(String id) {
        Optional<Navigations> navigations = navigationRepository.findById(id);
        if (navigations.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", navigations, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Navigations> navigationsList = navigationRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", navigationsList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Navigations> navigations = navigationRepository.findById(id);
        if (navigations.isPresent()) {
            navigationRepository.delete(navigations.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

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

            return new ResponseEntity<>(new MessageResponse("Successfully Updated", navigationRepository.save(navigationsOptional.get()), false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }
}
