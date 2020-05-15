package com.zfans.dao;

import com.zfans.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zfans
 * @date 2020/05/12 20:32
 */
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    Brand findByName(String name);
    Brand findByWebsite(String website);
    Brand findByLogo(String logo);
}
