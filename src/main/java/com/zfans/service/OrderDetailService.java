package com.zfans.service;

import com.zfans.dao.OrderDetailRepository;
import com.zfans.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Zfans
 * @date 2020/05/20 13:14
 */
@Service
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public Page<OrderDetail> listOrderDetail(Pageable pageable, Long id) {
        return orderDetailRepository.findAll((Specification<OrderDetail>) (root, criteriaQuery, criteriaBuilder) -> {
            if (id != null) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("orderMaster").get("id"), id));
            }
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
