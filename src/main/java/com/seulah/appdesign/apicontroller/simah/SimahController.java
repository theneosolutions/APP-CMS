package com.seulah.appdesign.apicontroller.simah;

import com.seulah.appdesign.apicontroller.simah.service.FileTransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms/")
public class SimahController {
    private final FileTransferService fileTransferService;

    public SimahController(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }

    @GetMapping("test")
    public String sftUpload() {
        fileTransferService.sftpSessionFactory();
        System.out.println("Arham");
        return "hello";
    }
}
