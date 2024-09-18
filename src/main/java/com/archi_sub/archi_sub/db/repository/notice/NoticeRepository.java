package com.archi_sub.archi_sub.db.repository.notice;

import com.archi_sub.archi_sub.db.entity.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByIdIn(List<Long> id);
}
