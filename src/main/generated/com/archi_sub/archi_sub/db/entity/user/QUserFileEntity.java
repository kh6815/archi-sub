package com.archi_sub.archi_sub.db.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFileEntity is a Querydsl query type for UserFileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFileEntity extends EntityPathBase<UserFileEntity> {

    private static final long serialVersionUID = 26603089L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFileEntity userFileEntity = new QUserFileEntity("userFileEntity");

    public final EnumPath<com.archi_sub.archi_sub.common.enumobj.BooleanFlag> delYn = createEnum("delYn", com.archi_sub.archi_sub.common.enumobj.BooleanFlag.class);

    public final com.archi_sub.archi_sub.db.entity.file.QFileEntity file;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserEntity user;

    public QUserFileEntity(String variable) {
        this(UserFileEntity.class, forVariable(variable), INITS);
    }

    public QUserFileEntity(Path<? extends UserFileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFileEntity(PathMetadata metadata, PathInits inits) {
        this(UserFileEntity.class, metadata, inits);
    }

    public QUserFileEntity(Class<? extends UserFileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new com.archi_sub.archi_sub.db.entity.file.QFileEntity(forProperty("file")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

