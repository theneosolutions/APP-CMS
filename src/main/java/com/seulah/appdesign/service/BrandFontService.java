package com.seulah.appdesign.service;


import com.seulah.appdesign.entity.Branding;
import com.seulah.appdesign.entity.BrandingFont;
import com.seulah.appdesign.entity.FontFamily;
import com.seulah.appdesign.repository.BrandFontRepository;
import com.seulah.appdesign.repository.BrandingRepository;
import com.seulah.appdesign.repository.FontFamilyRepository;
import com.seulah.appdesign.request.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.seulah.appdesign.utils.Constants.SUCCESS;


@Service
@Slf4j
public class BrandFontService {

    private final BrandFontRepository brandFontRepository;
    private final BrandingRepository brandingRepository;

    private final FileUploadService fileUploadService;

    private final FontFamilyRepository fontFamilyRepository;

    public BrandFontService(BrandFontRepository brandFontRepository, BrandingRepository brandingRepository, FileUploadService fileUploadService, FontFamilyRepository fontFamilyRepository) {
        this.brandFontRepository = brandFontRepository;
        this.brandingRepository = brandingRepository;
        this.fileUploadService = fileUploadService;
        this.fontFamilyRepository = fontFamilyRepository;
    }


    public ResponseEntity<MessageResponse> saveBrandingFont(MultipartFile[] fontFiles, String brandId) {
        Optional<Branding> brandingOptional = brandingRepository.findById(brandId);
        if (brandingOptional.isPresent()) {
            for (MultipartFile file : fontFiles) {
                try {
                    fileUploadService.uploadFile(file);
                } catch (Exception e) {
                    log.error("Exception during upload file ", e);
                }
                saveToDatabase(file, file.getOriginalFilename(), brandId);
            }
            log.info("Saved brand font data successfully");
            return new ResponseEntity<>(new MessageResponse("Successfully Uploaded", null, false), HttpStatus.OK);
        }
        log.info("No data found against brand id {}", brandId);
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
        List<BrandingFont> brandingFonts = brandFontRepository.findAllByBrandId(id);
        if (brandingFonts != null && !brandingFonts.isEmpty()) {
            brandingFonts.forEach(font -> {
                fileUploadService.deleteFile(font.getFont());
                brandFontRepository.delete(font);

            });
            log.info("delete brand font from database");
            return new ResponseEntity<>(new MessageResponse(SUCCESS, null, false), HttpStatus.OK);
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


    public ResponseEntity<MessageResponse> saveFontFamily(String brandId, Object response) {

        FontFamily fontFamily = fontFamilyRepository.findByBrandId(brandId);
        if (fontFamily == null) {
            fontFamily = new FontFamily();
        }
        fontFamily.setBrandId(brandId);
        if (fontFamily.getResponse() == null || fontFamily.getResponse().isEmpty()) {
            fontFamily.setResponse(Collections.singletonList(response));
        } else {
            AtomicReference<Boolean> flag = new AtomicReference<>(false);
            fontFamily.getResponse().stream().filter(res -> res.equals(response)).forEach(r -> flag.set(Boolean.TRUE));
            if (Boolean.TRUE.equals(flag.get())) {
                return new ResponseEntity<>(new MessageResponse("Already Exist", null, false), HttpStatus.OK);
            }
            fontFamily.getResponse().add(response);
        }
        fontFamily = fontFamilyRepository.save(fontFamily);

        return new ResponseEntity<>(new MessageResponse("Font Family Saved Successfully", fontFamily, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getFontFile(String brandId) {
        FontFamily fontFamily = fontFamilyRepository.findByBrandId(brandId);
        if (fontFamily != null) {
            List<String> responses = new ArrayList<>();
            try {
                fontFamily.getResponse().forEach(response -> {


                    Map<String, Object> jsonResponse = (Map<String, Object>) ((LinkedHashMap) response).get("files");
                    Object menuUrls = ((LinkedHashMap) response).get("menu");

                    List<String> urls = extractUrls(jsonResponse);
                    List<String> menuUrl = extractUrlsForObject(menuUrls);
                    urls.forEach(url -> {
                        try {
                            responses.add(getBase64EncodedImage(url));
                        } catch (IOException e) {
                            log.info("Exception", e);
                        }
                    });

                    menuUrl.forEach(url -> {
                        try {
                            responses.add(getBase64EncodedImage(url));
                        } catch (IOException e) {
                            log.info("Exception", e);
                        }
                    });

                });
            } catch (Exception e) {
                log.error("Exception during getting URL", e);
            }
            return new ResponseEntity<>(new MessageResponse(SUCCESS, responses, true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("NO_RECORD_FOUND", null, true), HttpStatus.BAD_REQUEST);

    }

    public static List<String> extractUrls(Map<String, Object> files) {
        List<String> urls = new ArrayList<>();

        for (Map.Entry<String, Object> entry : files.entrySet()) {
            Object fileUrlObject = entry.getValue();

            if (fileUrlObject instanceof String) {
                String fileUrl = (String) fileUrlObject;

                if (fileUrl.contains("http")) {
                    String url = fileUrl.substring(fileUrl.indexOf("http"), fileUrl.indexOf("ttf")) + "ttf";
                    urls.add(url);
                }
            }
        }

        return urls;
    }

    public static List<String> extractUrlsForObject(Object files) {
        List<String> urls = new ArrayList<>();
        if (files instanceof String) {
            String fileUrl = (String) files;

            if (fileUrl.contains("http")) {
                String url = fileUrl.substring(fileUrl.indexOf("http"), fileUrl.indexOf("ttf")) + "ttf";
                urls.add(url);
            }
        }

        return urls;
    }


    public String getBase64EncodedImage(String uri) throws IOException {
        java.net.URL url = new java.net.URL(uri);
        InputStream is = url.openStream();
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        return Base64.getEncoder().encodeToString(bytes);
    }


    public ResponseEntity<MessageResponse> getFontFamilyForAdmin(String brandId) {
        FontFamily fontFamily = fontFamilyRepository.findByBrandId(brandId);
        if (fontFamily != null) {
            return new ResponseEntity<>(new MessageResponse(SUCCESS, fontFamily, true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", null, true), HttpStatus.OK);
    }
}

