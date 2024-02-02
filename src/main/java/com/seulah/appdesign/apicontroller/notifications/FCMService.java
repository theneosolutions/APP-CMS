package com.seulah.appdesign.apicontroller.notifications;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FCMService {


    @Autowired
    private FirebaseMessaging firebaseMessagingConfig;

    public String sendNotification(NotificationRequest note, String topic) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();

        Message message = Message
                .builder()
                .setToken(topic)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        return firebaseMessagingConfig.send(message);
    }

}
