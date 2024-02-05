package com.seulah.appdesign.apicontroller.notifications;

import com.google.firebase.messaging.*;
import com.seulah.appdesign.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FCMService {


    @Autowired
    private FirebaseMessaging firebaseMessagingConfig;
    private final FileUploadService fileUploadService;
    private String s3Url = "https://seulahbucket.s3.amazonaws.com/";
    public FCMService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public String sendNotification(String subject, String content, MultipartFile file, String topic) throws FirebaseMessagingException {
        fileUploadService.uploadFile(file);
        Notification notification = Notification
                .builder()
                .setTitle(subject)
                .setBody(content)
                .setImage(s3Url+file.getOriginalFilename())
                .build();

        Message message = Message
                .builder()
                .setToken(topic)
                .setNotification(notification)
                .build();

        return firebaseMessagingConfig.send(message);
    }

}
