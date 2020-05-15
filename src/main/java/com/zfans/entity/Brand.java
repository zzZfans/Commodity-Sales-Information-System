package com.zfans.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/12 20:17
 */
@Data
@NoArgsConstructor
@Entity
//@Proxy(lazy = false)  //关闭懒加载 解决（单元测试不通过）：could not initialize proxy [com.zfans.entity.Brand#x] - no Session
public class Brand {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String website;
    private String logo;

    @OneToMany(mappedBy = "brand")
    private List<Commodity> commodityList = new ArrayList<>();
}
