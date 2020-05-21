package com.zfans.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Zfans
 * @date 2020/05/13 23:05
 */
@Data
@NoArgsConstructor
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String orderDetailNo;
    private Integer orderQuantity;
    @Column(precision = 8, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne
    private OrderMaster orderMaster;

    @ManyToOne
    private Commodity commodity;
}
