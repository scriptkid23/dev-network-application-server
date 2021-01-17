package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.dto.ListNotificationDTO;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM notification WHERE user_id=:id")
    List<ListNotificationDTO> getListNotification(@Param("id") Integer userId);



}
