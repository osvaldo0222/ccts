package com.project.ccts.service;

import com.project.ccts.model.entities.Notification;
import com.project.ccts.model.entities.NotificationToken;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.repository.NotificationRepository;
import com.project.ccts.service.common.AbstractCrud;
import io.github.jav.exposerversdk.ExpoMessageSound;
import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class NotificationService extends AbstractCrud<Notification, Long> {

    private NotificationRepository notificationRepository;

    @Autowired
    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public JpaRepository<Notification, Long> getDao() {
        return notificationRepository;
    }

    public List<Notification> findByUserCredentialAndOrderBySendDateDataDesc(UserCredential userCredential, Pageable pageable) {
        return notificationRepository.findByUserCredentialOrderBySendDateDesc(userCredential, pageable).getContent();
    }

    public void sendNotificationToUser(Notification notification) {
        //Saving the notification
        createOrUpdate(notification);

        //Push notification
        PushClient pushClient = new PushClient();
        List<ExpoPushMessage> messages = new ArrayList<>();
        Set<NotificationToken> notificationTokens = notification.getUserCredential().getNotificationToken();
        if (notificationTokens == null) {
            return;
        }
        for (NotificationToken token : notificationTokens) {
            ExpoPushMessage epm = new ExpoPushMessage(token.getToken());
            epm.channelId = "default";
            epm.sound = new ExpoMessageSound("default");
            epm.title = notification.getTitle();
            epm.subtitle = notification.getSubtitle();
            epm.body = notification.getMessageBody();
            messages.add(epm);
        }

        List<List<ExpoPushMessage>> chunks = pushClient.chunkPushNotifications(messages);

        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(pushClient.sendPushNotificationsAsync(chunk));
        }
    }
}
