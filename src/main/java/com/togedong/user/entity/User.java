package com.togedong.user.entity;

import com.togedong.auth.dto.UserResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String userId;

    private String password;

    public UserResponse toDto() {
        return new UserResponse(userId, userName);
    }

    @Builder
    public User(final String userId, final String password, final String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }
}
