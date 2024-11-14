package com.namng7.datn_v1.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int role;
}
