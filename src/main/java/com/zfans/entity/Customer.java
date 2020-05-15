package com.zfans.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/13 22:20
 */
@Data
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String customerNumber;
    private String name;
    private String phoneNumber;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<OrderMaster> orderMasterList = new ArrayList<>();
}
