package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.dto.ListFriendDTO;
import com.nuchat.capricorn.dto.UserContactDTO;
import com.nuchat.capricorn.model.UserContact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserContactRepository extends CrudRepository<UserContact,Integer> {

    @Query(nativeQuery = true,value = "select * from get_list_friend(:id)")  // call store procedure
    List<ListFriendDTO> getListFriend(@Param("id")Integer id);

}