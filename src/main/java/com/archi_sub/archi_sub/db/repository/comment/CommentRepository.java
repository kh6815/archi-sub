package com.archi_sub.archi_sub.db.repository.comment;

import com.archi_sub.archi_sub.db.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
