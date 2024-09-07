package com.archi_sub.archi_sub.db.entity.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = -2118076489L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final ListPath<CommentEntity, QCommentEntity> children = this.<CommentEntity, QCommentEntity>createList("children", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    public final StringPath comment = createString("comment");

    public final ListPath<com.archi_sub.archi_sub.db.entity.like.CommentLikeEntity, com.archi_sub.archi_sub.db.entity.like.QCommentLikeEntity> commentLikes = this.<com.archi_sub.archi_sub.db.entity.like.CommentLikeEntity, com.archi_sub.archi_sub.db.entity.like.QCommentLikeEntity>createList("commentLikes", com.archi_sub.archi_sub.db.entity.like.CommentLikeEntity.class, com.archi_sub.archi_sub.db.entity.like.QCommentLikeEntity.class, PathInits.DIRECT2);

    public final com.archi_sub.archi_sub.db.entity.content.QContentEntity content;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final EnumPath<com.archi_sub.archi_sub.common.enumobj.BooleanFlag> delYn = createEnum("delYn", com.archi_sub.archi_sub.common.enumobj.BooleanFlag.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isContentAuthor = createBoolean("isContentAuthor");

    public final QCommentEntity parent;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.archi_sub.archi_sub.db.entity.user.QUserEntity user;

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new com.archi_sub.archi_sub.db.entity.content.QContentEntity(forProperty("content"), inits.get("content")) : null;
        this.parent = inits.isInitialized("parent") ? new QCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.archi_sub.archi_sub.db.entity.user.QUserEntity(forProperty("user")) : null;
    }

}

