package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Banner;
import com.seulah.appdesign.repository.BannerRepository;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    private final BrandLogoService brandLogoService;

    public BannerService(BannerRepository bannerRepository, BrandLogoService brandLogoService) {
        this.bannerRepository = bannerRepository;
        this.brandLogoService = brandLogoService;
    }

    public ResponseEntity<MessageResponse> getBannerByID(String id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        return banner.map(ban -> new ResponseEntity<>(new MessageResponse("Success", ban, false), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK));
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


    public ResponseEntity<MessageResponse> saveBanner(MultipartFile bannerImage, String bannerDesign) {

        brandLogoService.saveToLocalDrive(bannerImage);
        saveToDatabase(bannerImage, bannerDesign);
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String bannerDesign) {
        Banner banner = new Banner();
        banner.setBannerDesign(bannerDesign);
        banner.setBannerImage(file.getOriginalFilename());
        bannerRepository.save(banner);
    }

    public ResponseEntity<Resource> getBannerImageById(String id) throws MalformedURLException {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            Resource resource = brandLogoService.loadFileAsResource(optionalBanner.get().getBannerImage());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
