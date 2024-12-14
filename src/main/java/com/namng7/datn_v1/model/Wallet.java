package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long company_id;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Long last_topup_id;

    @Column(nullable = false)
    private int status;
}
