package com.zfans.dao;

import com.zfans.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zfans
 * @date 2020/05/21 14:38
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long>, JpaSpecificationExecutor<OrderMaster> {
}
