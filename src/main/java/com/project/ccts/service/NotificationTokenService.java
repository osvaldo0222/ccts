package com.project.ccts.service;

import com.project.ccts.model.entities.NotificationToken;
import com.project.ccts.repository.NotificationTokenRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationTokenService extends AbstractCrud<NotificationToken, Long> {

    private NotificationTokenRepository notificationTokenRepository;

    @Autowired
    public void setNotificationTokenRepository(NotificationTokenRepository notificationTokenRepository) {
        this.notificationTokenRepository = notificationTokenRepository;
    }

    @Override
    public JpaRepository<NotificationToken, Long> getDao() {
        return notificationTokenRepository;
    }

    public NotificationToken findByToken(String token) {
        return notificationTokenRepository.findByToken(token);
    }
}
