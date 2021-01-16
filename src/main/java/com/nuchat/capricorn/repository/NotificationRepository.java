package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification,Integer> {
}
