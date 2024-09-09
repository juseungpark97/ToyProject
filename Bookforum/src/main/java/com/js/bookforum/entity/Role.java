package com.js.bookforum.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 역할 정보를 나타내는 엔티티")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "역할의 고유 ID", example = "1")
    private Long roleId;

    @Column(nullable = false, unique = true)
    @Schema(description = "역할 이름", example = "USER")
    private String name;

    @Schema(description = "역할에 대한 설명", example = "Standard user with limited privileges")
    private String description;

    @OneToMany(mappedBy = "role")
    @Schema(description = "이 역할을 가진 사용자 목록")
    private Set<User> users;
}