package com.zfans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/13 21:27
 */
@Data
@NoArgsConstructor
@Entity
public class Classification {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String picture;

    @OneToMany(mappedBy = "superClassification")
    private List<Classification> subClassificationList = new ArrayList<>();

    @ManyToOne
    private Classification superClassification;

    @OneToMany(mappedBy = "classification")
    private List<Commodity> commodityList= new ArrayList<>();
}
