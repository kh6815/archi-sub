package com.archi_sub.archi_sub.db.entity.content;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentEntity is a Querydsl query type for ContentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentEntity extends EntityPathBase<ContentEntity> {

    private static final long serialVersionUID = -229698837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentEntity contentEntity = new QContentEntity("contentEntity");

    public final com.archi_sub.archi_sub.db.entity.category.QCategoryEntity category;

    public final StringPath content = createString("content");

    public final ListPath<com.archi_sub.archi_sub.db.entity.like.ContentLikeEntity, com.archi_sub.archi_sub.db.entity.like.QContentLikeEntity> contentLikes = this.<com.archi_sub.archi_sub.db.entity.like.ContentLikeEntity, com.archi_sub.archi_sub.db.entity.like.QContentLikeEntity>createList("contentLikes", com.archi_sub.archi_sub.db.entity.like.ContentLikeEntity.class, com.archi_sub.archi_sub.db.entity.like.QContentLikeEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final EnumPath<com.archi_sub.archi_sub.common.enumobj.BooleanFlag> delYn = createEnum("delYn", com.archi_sub.archi_sub.common.enumobj.BooleanFlag.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.archi_sub.archi_sub.db.entity.user.QUserEntity user;

    public QContentEntity(String variable) {
        this(ContentEntity.class, forVariable(variable), INITS);
    }

    public QContentEntity(Path<? extends ContentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentEntity(PathMetadata metadata, PathInits inits) {
        this(ContentEntity.class, metadata, inits);
    }

    public QContentEntity(Class<? extends ContentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.archi_sub.archi_sub.db.entity.category.QCategoryEntity(forProperty("category"), inits.get("category")) : null;
        this.user = inits.isInitialized("user") ? new com.archi_sub.archi_sub.db.entity.user.QUserEntity(forProperty("user")) : null;
    }

}

