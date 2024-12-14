package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transaction_top_up")
public class TransactionTopUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long wallet_id;

    @Column(nullable = false)
    private int value;

    @Column
    private int wallet_balance;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private Long bussiness_id;

    @Column(nullable = false)
    private Date transaction_time;
}
