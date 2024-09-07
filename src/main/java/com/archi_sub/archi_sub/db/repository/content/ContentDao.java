package com.archi_sub.archi_sub.db.repository.content;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.common.error.CustomException;
import com.archi_sub.archi_sub.common.error.ExceptionCode;
import com.archi_sub.archi_sub.content.content.model.ContentModel;
import com.archi_sub.archi_sub.db.entity.category.QCategoryEntity;
import com.archi_sub.archi_sub.db.entity.content.ContentEntity;
import com.archi_sub.archi_sub.db.entity.content.ContentFileEntity;
import com.archi_sub.archi_sub.db.entity.content.QContentEntity;
import com.archi_sub.archi_sub.db.entity.content.QContentFileEntity;
import com.archi_sub.archi_sub.db.entity.like.QContentLikeEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ContentDao {

    private final JPAQueryFactory jpaQueryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QCategoryEntity qCategoryEntity = QCategoryEntity.categoryEntity;
    private final QContentEntity qContentEntity = QContentEntity.contentEntity;

    private final QContentFileEntity qContentFileEntity = QContentFileEntity.contentFileEntity;

    private final QContentLikeEntity qContentLikeEntity = QContentLikeEntity.contentLikeEntity;


    public Page<ContentModel.ContentListDto> findContentPages(Long categoryId, Pageable pageable) throws Exception {

        List<ContentModel.ContentListDto> contentListDtoList = jpaQueryFactory
                .select(
                        Projections.constructor(ContentModel.ContentListDto.class,
                        qContentEntity.id,
                                qContentEntity.category.categoryName,
                                qContentEntity.title,
                                qContentEntity.content,
                                qContentEntity.updatedAt,
                                // 서브쿼리를 사용하여 해당 content의 좋아요 수를 계산
                                JPAExpressions
                                        .select(qContentLikeEntity.count())
                                        .from(qContentLikeEntity)
                                        .where(qContentLikeEntity.content.id.eq(qContentEntity.id))
                        )

                )
                .from(qContentEntity)
                .where(
                        qContentEntity.delYn.eq(BooleanFlag.N)
                                .and(dynamicContentCategoryBuilder(categoryId))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qContentEntity.updatedAt.desc())
//                .distinct()
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qContentEntity)
                .where(
                        qContentEntity.delYn.eq(BooleanFlag.N)
                                .and(dynamicContentCategoryBuilder(categoryId))
                )
                .stream().count();
        return new PageImpl<>(contentListDtoList, pageable, total);
    }

    public ContentEntity findContent(Long id) throws CustomException {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qContentEntity)
                                .leftJoin(qContentEntity.category, qCategoryEntity).fetchJoin()
                                .leftJoin(qContentEntity.user, qUserEntity).fetchJoin()
                                .leftJoin(qContentEntity.contentLikes, qContentLikeEntity).fetchJoin()
                                .where(qContentEntity.id.eq(id)
                                        .and(qContentEntity.delYn.eq(BooleanFlag.N)))
                                .fetchOne()

                )
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("Content [%s] is null", id)));
    }

    public ContentEntity findSimpleContent(Long id) throws CustomException {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qContentEntity)
                                .where(qContentEntity.id.eq(id)
                                        .and(qContentEntity.delYn.eq(BooleanFlag.N)))
                                .fetchOne()

                )
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("Content [%s] is null", id)));
    }

    public ContentEntity findContentWithUser(Long id) throws CustomException {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qContentEntity)
                                .leftJoin(qContentEntity.user, qUserEntity).fetchJoin()
                                .where(qContentEntity.id.eq(id)
                                        .and(qContentEntity.delYn.eq(BooleanFlag.N)))
                                .fetchOne()

                )
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, String.format("Content [%s] is null", id)));
    }

    public List<ContentFileEntity> findContentFileByContentId(Long contentId) throws CustomException {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qContentFileEntity)
                                .where(qContentFileEntity.content.id.eq(contentId)
                                    .and(qContentFileEntity.delYn.eq(BooleanFlag.N)))
                                .fetch()
                )
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, "해당하는 데이터가 없습니다."));
    }

    public List<ContentFileEntity> findContentFileByContentIds(List<Long> contentIds) throws CustomException{
        return
                Optional.ofNullable(
                                jpaQueryFactory
                                        .selectFrom(qContentFileEntity)
                                        .where(qContentFileEntity.content.id.in(contentIds))
                                        .fetch()
                        )
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST, "해당하는 데이터가 없습니다."));
    }

    private BooleanBuilder dynamicContentCategoryBuilder(Long categoryId) throws Exception {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (categoryId != 0) {
            return booleanBuilder.and(qContentEntity.category.id.eq(categoryId));
        }

        return booleanBuilder;
    }
}
