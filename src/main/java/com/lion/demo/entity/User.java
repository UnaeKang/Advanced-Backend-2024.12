package com.lion.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id // primary key
    private String uid;
    private String pwd;
    private String uname;
    private String email;
    private LocalDate regDate;
    private String role;
    private String provider;
    private String profileUrl;
}
