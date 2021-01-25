package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.dto.*;
import com.nuchat.capricorn.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM conversation WHERE channel_id=:id")
    Conversation getCoversationDetail(@Param("id") String channelId);

    @Query(nativeQuery = true,value ="SELECT * FROM get_list_message_log(:id)")
    List<ListMessageLogDTO> getListMessageLog(@Param("id") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM get_list_last_message_log(:id)")
    List<ListMessageLogGroupDTO> getListMessageLogGroup(@Param("id") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM get_member_of_conversation(:id)")
    List<MemberOfConversationDTO> getMemberOfConversation(@Param("id") Integer conversationId);

    @Query(nativeQuery = true,value = "SELECT participants.user_id \n" +
            "FROM conversation \n" +
            "INNER JOIN participants \n" +
            "ON conversation.id = participants.conversation_id\n" +
            "WHERE conversation.channel_id =:id")
    List<ListUserIdOfConversation> getListUserIdOfConversation(@Param("id") String channelId);

}
