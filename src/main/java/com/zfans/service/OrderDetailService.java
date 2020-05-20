package com.zfans.service;

import com.zfans.dao.OrderDetailRepository;
import com.zfans.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/20 13:14
 */
@Service
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.getOne(id);
    }

    public List<OrderDetail> listOrderDetail() {
        return orderDetailRepository.findAll();
    }

    public Page<OrderDetail> listOrderDetail(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
