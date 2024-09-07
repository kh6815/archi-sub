package com.archi_sub.archi_sub.db.repository.like;

import com.archi_sub.archi_sub.db.entity.like.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<CommentLikeEntity> findByUser_IdAndComment_Id(String userId, Long commentId);
}
