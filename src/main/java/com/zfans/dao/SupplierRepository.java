package com.zfans.dao;

import com.zfans.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zfans
 * @date 2020/05/12 23:34
 */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
