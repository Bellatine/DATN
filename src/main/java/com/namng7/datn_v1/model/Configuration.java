package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "configuration")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String key;

    @Column
    private String  content;

    @Column
    private int module_id;
}
