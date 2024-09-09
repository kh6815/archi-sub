package com.archi_sub.archi_sub.db.repository.content;

import com.archi_sub.archi_sub.db.entity.content.BestContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestContentRepository extends JpaRepository<BestContentEntity, Long> {
}
