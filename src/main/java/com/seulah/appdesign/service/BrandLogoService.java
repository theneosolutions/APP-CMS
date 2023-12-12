package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingLogo;
import com.seulah.appdesign.repository.BrandLogoRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BrandLogoService {

    private final BrandLogoRepository brandingLogoRepository;

    @Value("${image.path}")
    private String imagePath;

    private final BrandingRepository brandingRepository;

    public BrandLogoService(BrandLogoRepository brandingLogoRepository, BrandingRepository brandingRepository) {
        this.brandingLogoRepository = brandingLogoRepository;
        this.brandingRepository = brandingRepository;
    }

    @Transactional
    public ResponseEntity<MessageResponse> saveBrandingLogo(MultipartFile file, String brandId) throws IOException {
        Optional<Branding> branding = brandingRepository.findById(brandId);
        if (branding.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No brand exist with this id", null, false), HttpStatus.BAD_REQUEST);
        }
        if (!getFileExtension(file).equalsIgnoreCase("png") && !getFileExtension(file).equalsIgnoreCase("svg")) {
            log.error("Only PNG and SVG images are allowed.");
            return new ResponseEntity<>(new MessageResponse("Only PNG and SVG images are allowed. ", null, false), HttpStatus.BAD_REQUEST);
        }

        saveToLocalDrive(file);
        saveToDatabase(file, brandId);
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    private void saveToLocalDrive(MultipartFile file) throws IOException {
        String osName = getOperatingSystem();
        String localPath;
        if (osName.contains("Window")) {
            localPath = imagePath;
        } else {
            localPath = "/";
        }
        Files.createDirectories(Paths.get(localPath));
        Path filePath = Paths.get(localPath + File.separator + file.getOriginalFilename());

        log.info("File Contents: " + new String(file.getBytes()));

        file.getInputStream();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File saved to local drive: {}", filePath);
    }


    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return FilenameUtils.getExtension(originalFilename);
    }

    public String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public ResponseEntity<Resource> getLogoByBrandId(String brandId)  {
        List<BrandingLogo> brandingLogos = brandingLogoRepository.findAllByBrandId(brandId);
        if (brandingLogos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Resource> resources = brandingLogos.stream()
                .map(logo -> {
                    try {
                        return loadFileAsResource(logo.getLogo());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        Resource concatenatedResource = concatenateResources(resources);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + brandId + ".zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Adjust content type as needed
                .body(concatenatedResource);
    }

    private Resource loadFileAsResource(String fileName) throws MalformedURLException {
        String localPath = imagePath;

        Path filePath = Paths.get(localPath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found: " + fileName);
        }
    }

    private Resource concatenateResources(List<Resource> resources) {
        return resources.get(0);
    }

    private void saveToDatabase(MultipartFile file, String brandId) {
        BrandingLogo brandingLogo = new BrandingLogo();
        brandingLogo.setBrandId(brandId);
        brandingLogo.setLogo(file.getOriginalFilename());

        brandingLogoRepository.save(brandingLogo);
        log.info("Branding logo saved to the database");
    }
}

