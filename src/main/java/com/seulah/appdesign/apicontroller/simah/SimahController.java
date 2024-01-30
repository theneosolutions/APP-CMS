package com.seulah.appdesign.apicontroller.simah;

import com.jcraft.jsch.ChannelSftp;
import com.seulah.appdesign.apicontroller.simah.service.FileTransferService;
import org.springframework.integration.file.remote.session.SessionFactory;
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
    public String sftUpload(){
        SessionFactory<ChannelSftp.LsEntry> test = fileTransferService.sftpSessionFactory();
        System.out.println(test.getSession()+"Arham");
        return "hello";
    }
}
