package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.User;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsById(Integer id);
    User findByEmail(String email);


    @Transactional
    void deleteByEmail(String email);
}