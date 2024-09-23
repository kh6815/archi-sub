package com.archi_sub.archi_sub.db.repository.notification;

import com.archi_sub.archi_sub.db.entity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
