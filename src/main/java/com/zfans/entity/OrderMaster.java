package com.zfans.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/13 22:10
 */
@Data
@NoArgsConstructor
@Entity
public class OrderMaster {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String orderNo;
    private String contactNumber;
    private String receivingAddress;
    private String postalCode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "orderMaster", cascade = {CascadeType.REMOVE})
    private List<OrderDetail> orderDetailList = new ArrayList<>();
}
