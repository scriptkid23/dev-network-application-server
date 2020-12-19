package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation,Integer> {
}
