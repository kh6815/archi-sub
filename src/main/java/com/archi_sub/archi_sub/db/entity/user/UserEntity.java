package com.archi_sub.archi_sub.db.entity.user;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.common.enumobj.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class) // 엔티티의 변경을 자동 감지, AuditingEntityListener -> 생성, 변경 날짜를 자동 세팅(@CreatedDate, @LastModifiedDate)
@NoArgsConstructor // 기본 생성자 세팅
@DynamicInsert // 값이 있는것만 DB에 insert 하여, null이 DB에 들어가는 걸 방지
@DynamicUpdate // 변경된 필드들만 UPDATE 문에 포함되도록 최적화
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @Column(name = "ID")
    @NotNull
    private String id;

    @Column(name = "PW")
    private String pw;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NICK_NAME", unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false) //default USER
    private RoleType role;

    // provider : google이 들어감
    @Column(name = "PROVIDER")
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN", columnDefinition = "enum('Y', 'N') default 'N'", nullable = false)
    private BooleanFlag delYn;

    @Column(name = "CREATED_AT", columnDefinition = "datetime default CURRENT_TIMESTAMP", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public UserEntity(String id, String pw, String email, String nickName, String provider, String providerId){
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.nickName = nickName;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void changePassword(String newPassword){
        this.pw = newPassword;
    }
    public void changeNickName(String newNickName) {
        this.nickName = newNickName;
    }
    public void deleteUser(){
        this.delYn = BooleanFlag.Y;
    }
    public void comeBackUser() {
        this.delYn = BooleanFlag.N;
    }
}
