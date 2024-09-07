package com.archi_sub.archi_sub.db.repository.user;

import com.archi_sub.archi_sub.db.entity.user.UserFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileRepository extends JpaRepository<UserFileEntity, Long> {
}
