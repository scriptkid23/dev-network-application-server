package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.Messages;
import org.springframework.data.repository.CrudRepository;

public interface  MessagesRepository extends CrudRepository<Messages,Integer> {
}