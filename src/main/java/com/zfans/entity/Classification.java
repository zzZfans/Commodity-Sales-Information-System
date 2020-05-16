package com.zfans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;
import org.hibernate.sql.Delete;

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

    @OneToMany(mappedBy = "superClassification", cascade = {CascadeType.REMOVE})
    private List<Classification> subClassificationList = new ArrayList<>();

    @ManyToOne
    private Classification superClassification;

    @OneToMany(mappedBy = "classification")
    private List<Commodity> commodityList = new ArrayList<>();

    @Override
    public String toString() {
        if (superClassification != null) {
            superClassification.getSubClassificationList().clear();
        }
        subClassificationList.forEach(sub -> {
            sub.setSuperClassification(null);
        });

        return "Classification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", subClassificationList=" + subClassificationList +
                ", superClassification=" + superClassification +
                ", commodityList=" + commodityList +
                '}';
    }
}
