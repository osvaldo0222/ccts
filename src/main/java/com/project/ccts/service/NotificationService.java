package com.project.ccts.service;

import com.project.ccts.model.entities.*;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class NotificationService extends AbstractCrud<Notification, Long> {

    private NotificationRepository notificationRepository;
    private ProjectStatisticsService projectStatisticsService;

    @Autowired
    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Autowired
    public void setProjectStatisticsService(ProjectStatisticsService projectStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
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

        //Statistics
        projectStatisticsService.addNotificationSent();

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

    public void sendNotificationBasedOnStatus(HealthStatus status) {
        if (status.getTest() != null && status.getTest().getStatus()) {
            UserCredential userCredential = status.getPerson().getUserCredential();
            if (userCredential != null) {
                sendNotificationToUser(new Notification("CCTS-Resultados", "", "Has dado positivo al virus. Revisa tu ultimo reporte!", new NotificationData("HealthStatus"), userCredential));
            }
        } else if (status.getTest() != null && !status.getTest().getStatus()) {
            UserCredential userCredential = status.getPerson().getUserCredential();
            if (userCredential != null) {
                sendNotificationToUser(new Notification("CCTS-Resultados", "", "Has dado negativo al virus. Revisa tu ultimo reporte!", new NotificationData("HealthStatus"), userCredential));
            }
        }
    }

    public void sendNotifications(Collection<PersonAndKInfectors> personAndKInfectors) {
        personAndKInfectors.stream().forEach((person -> {
            if (person.getPerson().getUserCredential() != null) {
                Notification notification = new Notification("CCTS-Alerta", "", String.format("Se ha detectado %s contacto cercano con contagiado del COVID-19. Registra tus sintomas...", person.getK().toString()), new NotificationData("HealthStatus", "NewReport"), person.getPerson().getUserCredential());
                sendNotificationToUser(notification);
            }
        }));
    }
}
