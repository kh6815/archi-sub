package com.archi_sub.archi_sub.db.entity.auth;

import com.archi_sub.archi_sub.db.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TOKEN_PAIR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenPairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", unique = true)
    private UserEntity user;

    public static TokenPairEntity createTokenPair(String accessToken, String refreshToken, UserEntity user) {
        TokenPairEntity tokenPair = new TokenPairEntity();
        tokenPair.accessToken = accessToken;
        tokenPair.refreshToken = refreshToken;
        tokenPair.user = user;
        return tokenPair;
    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
