package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.Contacts;
import com.nuchat.capricorn.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ContactsRepository extends CrudRepository<Contacts,Integer> {
    boolean existsByEmail(String email);

    boolean existsById(Integer id);
    Contacts findByEmail(String email);


    @Transactional
    void deleteByEmail(String email);
}