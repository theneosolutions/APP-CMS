package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.*;
import com.seulah.appdesign.repository.*;
import com.seulah.appdesign.request.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;


@Service
@Slf4j
public class BrandFontService {

    private final BrandFontRepository brandFontRepository;
    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    public BrandFontService(BrandFontRepository brandFontRepository, BrandingRepository brandingRepository, FileUploadService fileUploadService) {
        this.brandFontRepository = brandFontRepository;
        this.brandingRepository = brandingRepository;
        this.fileUploadService = fileUploadService;
    }


    public ResponseEntity<MessageResponse> saveBrandingFont(List<MultipartFile> fontFiles, String brandId) {
        Optional<Branding> brandingOptional = brandingRepository.findById(brandId);
        if (brandingOptional.isPresent()) {
            fontFiles.forEach(file -> {
                fileUploadService.uploadFile(file);
                saveToDatabase(file, file.getOriginalFilename(), brandId);
            });
            return new ResponseEntity<>(new MessageResponse("Successfully Uploaded", null, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    private void saveToDatabase(MultipartFile file, String name, String brandId) {
        BrandingFont brandingFont = brandFontRepository.findByName(brandId);
        if (brandingFont == null) {
            brandingFont = new BrandingFont();
            brandingFont.setFont(file.getOriginalFilename());
            brandingFont.setBrandId(brandId);
            brandingFont.setName(name);
        }
        brandFontRepository.save(brandingFont);
        log.info("Branding logo saved to the database");
    }


    public ResponseEntity<MessageResponse> deleteById(String id) {
        Optional<BrandingFont> brandingFont = brandFontRepository.findById(id);
        if (brandingFont.isPresent()) {
            brandFontRepository.delete(brandingFont.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<byte[]> getFontFileUrlByBrandId(String brandId) {
        List<BrandingFont> brandingFonts = brandFontRepository.findAllByBrandId(brandId);

        if (brandingFonts != null && !brandingFonts.isEmpty()) {
            List<ByteArrayOutputStream> downloadInputStreams = new ArrayList<>();

            brandingFonts.forEach(font -> {
                ByteArrayOutputStream downloadInputStream = fileUploadService.download(font.getName());
                downloadInputStreams.add(downloadInputStream);
            });

            ByteArrayOutputStream resultStream = mergeByteArrayOutputStreams(downloadInputStreams);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + brandingFonts.get(0) + "\"")
                    .body(resultStream.toByteArray());
        }

        return ResponseEntity.notFound().build();
    }

    private ByteArrayOutputStream mergeByteArrayOutputStreams(List<ByteArrayOutputStream> streams) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        streams.forEach(stream -> result.writeBytes(stream.toByteArray()));
        return result;
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        return switch (fileExtension) {
            case "txt" -> MediaType.TEXT_PLAIN;
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg" -> MediaType.IMAGE_JPEG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

}

