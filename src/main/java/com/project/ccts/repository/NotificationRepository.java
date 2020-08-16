package com.project.ccts.repository;

import com.project.ccts.model.entities.Notification;
import com.project.ccts.model.entities.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserCredentialOrderBySendDateDesc(UserCredential userCredential, Pageable pageable);
}
