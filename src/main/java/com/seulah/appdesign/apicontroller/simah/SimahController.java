package com.seulah.appdesign.apicontroller.simah;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.seulah.appdesign.apicontroller.simah.service.FileTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/cms/")
public class SimahController {
    private final FileTransferService fileTransferService;

    public SimahController(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }
    @PostMapping("/test")
    public ResponseEntity<String> uploadFile() throws SftpException, IOException, JSchException, SftpException, IOException {
        fileTransferService.test();
        return ResponseEntity.ok("File uploaded successfully: ");
    }

//    @PostMapping("/test")
//    public ResponseEntity<String> uploadFile(@RequestParam("directory") String remoteDirectory,
//                                             @RequestParam("localFilePath") String localFilePath) throws SftpException, IOException, JSchException, SftpException, IOException {
//        String remoteFileName = "uploaded_" + new Date().getTime() + ".txt";
//     //   fileTransferService.uploadFile(remoteDirectory, localFilePath, remoteFileName);
//
//        return ResponseEntity.ok("File uploaded successfully: " + remoteFileName);
//    }
}
