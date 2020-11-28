package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.Users;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<Users, Integer> {
    boolean existsByEmail(String email);

    boolean existsById(Integer id);
    Users findByEmail(String email);


    @Transactional
    void deleteByEmail(String email);
}
