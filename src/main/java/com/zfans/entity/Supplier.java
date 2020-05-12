package com.zfans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Zfans
 * @date 2020/05/12 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
@Proxy(lazy = false)  //关闭懒加载 解决（单元测试不通过）：could not initialize proxy [com.zfans.entity.Supplier#x] - no Session
public class Supplier {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String contact;
    private String contactNumber;
    private String supplierProfile;
}
