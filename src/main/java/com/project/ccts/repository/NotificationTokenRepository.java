package com.project.ccts.repository;

import com.project.ccts.model.entities.NotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {

    NotificationToken findByToken(String token);
}
