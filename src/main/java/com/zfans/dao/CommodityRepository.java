package com.zfans.dao;

import com.zfans.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zfans
 * @date 2020/05/17 19:14
 */
public interface CommodityRepository extends JpaRepository<Commodity, Long>, JpaSpecificationExecutor<Commodity> {
    Commodity findByCode(String code);

    Commodity findByNameSpecificationModel(String nameSpecificationModel);

    Commodity findByModel(String model);

    Commodity findByUnit(String unit);

    Commodity findByPicture(String picture);

    Commodity findByIntroduce(String introduce);

}
