package com.archi_sub.archi_sub.db.repository.notice;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.common.error.CustomException;
import com.archi_sub.archi_sub.common.error.ExceptionCode;
import com.archi_sub.archi_sub.db.entity.category.QCategoryEntity;
import com.archi_sub.archi_sub.db.entity.content.QBestContentEntity;
import com.archi_sub.archi_sub.db.entity.content.QContentEntity;
import com.archi_sub.archi_sub.db.entity.content.QContentFileEntity;
import com.archi_sub.archi_sub.db.entity.file.QFileEntity;
import com.archi_sub.archi_sub.db.entity.like.QContentLikeEntity;
import com.archi_sub.archi_sub.db.entity.notice.NoticeEntity;
import com.archi_sub.archi_sub.db.entity.notice.NoticeFileEntity;
import com.archi_sub.archi_sub.db.entity.notice.QNoticeEntity;
import com.archi_sub.archi_sub.db.entity.notice.QNoticeFileEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoticeDao {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QCategoryEntity qCategoryEntity = QCategoryEntity.categoryEntity;
    private final QContentEntity qContentEntity = QContentEntity.contentEntity;

    private final QContentFileEntity qContentFileEntity = QContentFileEntity.contentFileEntity;

    private final QContentLikeEntity qContentLikeEntity = QContentLikeEntity.contentLikeEntity;

    private final QBestContentEntity qBestContentEntity = QBestContentEntity.bestContentEntity;

    private final QFileEntity qFileEntity = QFileEntity.fileEntity;

    private final QNoticeEntity qNoticeEntity = QNoticeEntity.noticeEntity;

    private final QNoticeFileEntity qNoticeFileEntity = QNoticeFileEntity.noticeFileEntity;

    public NoticeEntity findNotice(Long id) throws CustomException {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qNoticeEntity)
                                .leftJoin(qNoticeEntity.user, qUserEntity).fetchJoin()
                                .where(qNoticeEntity.id.eq(id)
                                        .and(qNoticeEntity.delYn.eq(BooleanFlag.N)))
                                .fetchOne()

                )
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("Notice [%s] is null", id)));
    }

    public List<NoticeFileEntity> findNoticeFileByNoticeId(Long noticeId) throws CustomException {
        return
                Optional.ofNullable(
                                jpaQueryFactory
                                        .selectFrom(qNoticeFileEntity)
                                        .where(qNoticeFileEntity.notice.id.eq(noticeId)
                                                .and(qNoticeFileEntity.delYn.eq(BooleanFlag.N)))
                                        .fetch()
                        )
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, "해당하는 데이터가 없습니다."));
    }

    public List<NoticeFileEntity> findNoticeFileByNoticeIds(List<Long> noticeIds) throws CustomException{
        return
                Optional.ofNullable(
                                jpaQueryFactory
                                        .selectFrom(qNoticeFileEntity)
                                        .where(qNoticeFileEntity.notice.id.in(noticeIds))
                                        .fetch()
                        )
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, "해당하는 데이터가 없습니다."));
    }

    public List<NoticeFileEntity> findNoticeFileByFileIds(List<Long> fileIds) throws CustomException{
        return
                Optional.ofNullable(
                                jpaQueryFactory
                                        .selectFrom(qNoticeFileEntity)
                                        .where(qNoticeFileEntity.file.id.in(fileIds))
                                        .fetch()
                        )
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, "해당하는 데이터가 없습니다."));
    }

    public void updateDelYnContentNoticeListByNoticeIds(List<Long> noticeIds) {
        jpaQueryFactory
                .update(qNoticeFileEntity)
                .set(qNoticeFileEntity.delYn, BooleanFlag.Y)
                .where(qNoticeFileEntity.id.in(noticeIds))
                .execute();
    }
}
