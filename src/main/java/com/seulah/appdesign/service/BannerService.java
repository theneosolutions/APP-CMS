package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Banner;
import com.seulah.appdesign.repository.BannerRepository;
import com.seulah.appdesign.request.BannerRequest;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public ResponseEntity<MessageResponse> saveBanner(BannerRequest bannerRequest) {
        Banner banner = new Banner();

        banner.setBannerDesign(bannerRequest.getBannerDesign());
        banner.setBannerImage(bannerRequest.getBannerImage());
        banner = bannerRepository.save(banner);

        return new ResponseEntity<>(new MessageResponse("Successfully Created Banner", banner, false), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> getBannerByID(String id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if (banner.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", banner, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Banner> bannerList = bannerRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", bannerList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if (banner.isPresent()) {
            bannerRepository.delete(banner.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(String id, BannerRequest bannerRequest) {
        Optional<Banner> bannerOptional = bannerRepository.findById(id);
        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();
            if (bannerRequest.getBannerDesign() != null && !bannerRequest.getBannerDesign().isEmpty()) {
                banner.setBannerDesign(bannerRequest.getBannerDesign());
            }
            if (bannerRequest.getBannerImage() != null && !bannerRequest.getBannerImage().isEmpty()) {
                banner.setBannerImage(bannerRequest.getBannerImage());
            }

            banner = bannerRepository.save(banner);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", banner, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }
}
