package com.zfans.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/13 21:34
 */
@Data
@NoArgsConstructor
@Entity
public class Commodity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    private String specification;
    private String model;
    private String nameSpecificationModel;
    private String unit;
    @Column(precision = 8, scale = 2)
    private BigDecimal marketValue;
    @Column(precision = 8, scale = 2)
    private BigDecimal salesPrice;
    @Column(precision = 8, scale = 2)
    private BigDecimal costPrice;
    private String picture;
    private String introduce;
    private Integer quantity;

    @Transient //不入库
    private String supplierIds;

    @ManyToOne
    private Brand brand;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Supplier> supplierList = new ArrayList<>();

    @OneToMany(mappedBy = "commodity")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @ManyToOne
    private Classification classification;

    public void initSupplierIds() {
        this.supplierIds = supplierListToSupplierIds(this.getSupplierList());
    }

    private String supplierListToSupplierIds(List<Supplier> supplierList) {
        if (!supplierList.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Supplier tag : supplierList) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return supplierIds;
        }
    }
}
