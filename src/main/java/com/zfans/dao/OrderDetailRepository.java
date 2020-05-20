package com.zfans.dao;

import com.zfans.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zfans
 * @date 2020/05/20 13:13
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
