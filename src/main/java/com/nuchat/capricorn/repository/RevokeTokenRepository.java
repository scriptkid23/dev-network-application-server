package com.nuchat.capricorn.repository;

import com.nuchat.capricorn.model.RevokeToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeTokenRepository extends JpaRepository<RevokeToken,Integer> {
    boolean existsByToken(String token);
}
