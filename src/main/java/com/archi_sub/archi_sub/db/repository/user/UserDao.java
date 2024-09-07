package com.archi_sub.archi_sub.db.repository.user;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.db.entity.auth.QTokenPairEntity;
import com.archi_sub.archi_sub.db.entity.file.QFileEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserFileEntity;
import com.archi_sub.archi_sub.db.entity.user.UserFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserDao {
    private final JPAQueryFactory jpaQueryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;

    private final QFileEntity qFileEntity = QFileEntity.fileEntity;

    private final QUserFileEntity qUserFileEntity = QUserFileEntity.userFileEntity;

    private final QTokenPairEntity qTokenPairEntity = QTokenPairEntity.tokenPairEntity;

    public Optional<UserFileEntity> findUserFileByUserId(String userId){
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qUserFileEntity)
                                .where(qUserFileEntity.user.id.eq(userId)
                                        .and(qUserFileEntity.delYn.eq(BooleanFlag.N))
                                )
                                .fetchOne()
                );
    }

    public Optional<UserFileEntity> findUserFileWithFileByUserId(String userId){
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(qUserFileEntity)
                                .leftJoin(qUserFileEntity.file, qFileEntity).fetchJoin()
                                .where(qUserFileEntity.user.id.eq(userId)
                                        .and(qUserFileEntity.delYn.eq(BooleanFlag.N))
                                )
                                .fetchOne()
                );
    }
}
