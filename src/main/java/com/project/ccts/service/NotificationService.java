package com.project.ccts.service;

import com.project.ccts.model.entities.Notification;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.repository.NotificationRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
