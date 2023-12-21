package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Banner;
import com.seulah.appdesign.repository.BannerRepository;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;
    private final FileUploadService fileUploadService;

    public BannerService(BannerRepository bannerRepository, FileUploadService fileUploadService) {
        this.bannerRepository = bannerRepository;
        this.fileUploadService = fileUploadService;
    }


    public ResponseEntity<MessageResponse> getBannerByID(String id) {
        HashMap<String, Object> response = new HashMap<>();
        Optional<Banner> banner = bannerRepository.findById(id);
        byte[] image = getBannerImageById(id);
        if (banner.isPresent()) {
            response.put("bannerDetail", banner);
        }
        response.put("image", image);
        return new ResponseEntity<>(new MessageResponse("Success", response, false), HttpStatus.OK);

    }

    public ResponseEntity<MessageResponse> getAll() {
        List<Banner> bannerList = bannerRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", bannerList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if (banner.isPresent()) {
            fileUploadService.deleteFile(banner.get().getBannerImage());
            bannerRepository.delete(banner.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> saveBanner(MultipartFile bannerImage, String bannerDesign, int height, int width) {

        fileUploadService.uploadFile(bannerImage);
        saveToDatabase(bannerImage, bannerDesign, height, width);
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String bannerDesign, int height, int width) {
        Banner banner = new Banner();
        banner.setBannerDesign(bannerDesign);
        banner.setWidth(width);
        banner.setHeight(height);
        banner.setBannerImage(file.getOriginalFilename());
        bannerRepository.save(banner);
    }

    public byte[] getBannerImageById(String id) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            String fileName = optionalBanner.get().getBannerImage();
            return fileUploadService.downloadFile(fileName);
        }
        return null;

    }
}
