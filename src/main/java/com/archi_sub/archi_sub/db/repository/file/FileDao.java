package com.archi_sub.archi_sub.db.repository.file;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.db.entity.auth.QTokenPairEntity;
import com.archi_sub.archi_sub.db.entity.content.QContentFileEntity;
import com.archi_sub.archi_sub.db.entity.file.FileEntity;
import com.archi_sub.archi_sub.db.entity.file.QFileEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserEntity;
import com.archi_sub.archi_sub.db.entity.user.QUserFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FileDao {
    private final JPAQueryFactory jpaQueryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;

    private final QFileEntity qFileEntity = QFileEntity.fileEntity;

    private final QUserFileEntity qUserFileEntity = QUserFileEntity.userFileEntity;

    private final QContentFileEntity qContentFileEntity = QContentFileEntity.contentFileEntity;

    public List<FileEntity> findDeleteFileByUserFileIds(List<Long> userFileIds){
        return
                jpaQueryFactory
                .select(qUserFileEntity.file)
                .from(qUserFileEntity)
                .where(qUserFileEntity.id.in(userFileIds))
                .fetch();
    }

    public List<FileEntity> findDeleteFileByContentFileIds(List<Long> contentFileIds){
        return
                jpaQueryFactory
                        .select(qContentFileEntity.file)
                        .from(qContentFileEntity)
                        .where(qContentFileEntity.id.in(contentFileIds))
                        .fetch();
    }
}
