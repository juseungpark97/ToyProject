package com.js.bookforum.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 정보를 나타내는 엔티티")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자의 고유 ID", example = "1")
    private Long userId;

    @Schema(description = "사용자의 이름", example = "John Doe")
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자의 이메일 주소", example = "john.doe@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "사용자의 비밀번호", example = "password123")
    private String password;

    @Schema(description = "사용자 가입 날짜", example = "2023-01-01")
    private Date registrationDate;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @Schema(description = "사용자의 역할 정보")
    private Role role;

    @OneToMany(mappedBy = "user")
    @Schema(description = "사용자가 대여한 책 정보 목록")
    private Set<Rental> rentals;

    @OneToMany(mappedBy = "user")
    @Schema(description = "사용자가 작성한 리뷰 목록")
    private Set<Review> reviews;
}