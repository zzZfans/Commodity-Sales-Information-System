package com.zfans.dao;

import com.zfans.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zfans
 * @date 2020/05/12 20:32
 */
public interface BrandRepository extends JpaRepository<Brand,Long> {
}
