package com.zfans.entity;

import ch.qos.logback.classic.db.names.ColumnName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

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

    @ManyToOne
    private Brand brand;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Supplier> supplierList = new ArrayList<>();

    @OneToOne(mappedBy = "commodity")
    private OrderDetail orderDetail;

    @ManyToOne
    private Classification classification;
}
