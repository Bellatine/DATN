package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transaction_buy_gamecode")
public class TransactionBuyGamecode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long wallet_before;

    @Column(nullable = false)
    private Long wallet_after;

    @Column(nullable = false)
    private Long wallet_consumption;

    @Column(nullable = false)
    private Long company_id;

    @Column(nullable = false)
    private Date transaction_time;

    @Column(nullable = false)
    private Integer total_item;

    @Column(nullable = false)
    private Long model_id;
}
