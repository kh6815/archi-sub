package com.archi_sub.archi_sub.db.repository.user;


import com.archi_sub.archi_sub.db.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsById(String userId);
    // 특정 닉네임이 이미 존재하는지 확인하는 메서드
    boolean existsByNickName(String nickName);
}
