package com.seulah.appdesign.apicontroller.notifications;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/cms/")
public class NotificationController {
    @Autowired
    private FCMService fcmService;
    private final List<String> deviceTokens = new ArrayList<>(); // In-memory storage for device tokens

    @PostMapping("/notification")
    public String sendNotification(@RequestParam String subject, @RequestParam String content, @RequestParam MultipartFile file, @RequestParam String topic, @RequestParam String navigation) throws ExecutionException, InterruptedException, FirebaseMessagingException {
        return fcmService.sendNotification(subject, content, file, topic, navigation);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDeviceToken(@RequestBody String deviceToken) {
        if (!deviceTokens.contains(deviceToken)) {
            deviceTokens.add(deviceToken);
            return ResponseEntity.ok("Device token registered successfully.");
        } else {
            return ResponseEntity.ok("Device token already registered.");
        }
    }

}
