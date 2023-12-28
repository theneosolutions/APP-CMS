package com.seulah.appdesign.controller;

import com.seulah.appdesign.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Muhammad Mansoor
 */
@RestController
@RequestMapping("/api/v1/cms/upload")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/fileUpload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return fileUploadService.uploadFile(file);
    }

    @GetMapping("/download")
    public byte[] downloadFile(@RequestParam(value = "file") String fileName) {
        return fileUploadService.downloadFile(fileName);
    }
}
