package com.kh.toy.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String username;
    private String password;
    private String email;
    private String condition;
}
