package com.archi_sub.archi_sub.db.repository.auth;

import com.archi_sub.archi_sub.db.entity.auth.TokenPairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenPairRepository extends JpaRepository<TokenPairEntity, Long> {
    Optional<TokenPairEntity> findByUserId(String userId);
//    Optional<TokenPairEntity> findByUserIdWithUser(String userId);
}
