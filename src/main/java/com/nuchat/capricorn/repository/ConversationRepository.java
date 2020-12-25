package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.dto.ListFriendDTO;
import com.nuchat.capricorn.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation,Integer> {

    @Query(nativeQuery = true,value = "select * from conversation where channel_id=:id")
    Conversation getCoversationDetail(@Param("id") String channelId);
}
