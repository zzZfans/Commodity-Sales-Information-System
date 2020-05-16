package com.zfans.dao;

import com.zfans.entity.Brand;
import com.zfans.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zfans
 * @date 2020/05/16 19:00
 */
public interface ClassificationRepository extends JpaRepository<Classification, Long>, JpaSpecificationExecutor<Classification> {
    Classification findByName(String name);

    Classification findByPicture(String picture);
}
