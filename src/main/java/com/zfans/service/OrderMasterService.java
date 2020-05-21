package com.zfans.service;

import com.zfans.dao.OrderMasterRepository;
import com.zfans.entity.OrderMaster;
import com.zfans.vo.OrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/21 14:38
 */
@Service
public class OrderMasterService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    public Page<OrderMaster> listOrderMaster(Pageable pageable) {
        return orderMasterRepository.findAll(pageable);
    }

    public Page<OrderMaster> listOrderMaster(Pageable pageable, OrderQuery orderQuery) {
        return orderMasterRepository.findAll((Specification<OrderMaster>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"".equals(orderQuery.getKeyword()) && orderQuery.getKeyword() != null) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("orderNo"), "%" + orderQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("customer").get("name"), "%" + orderQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("contactNumber"), "%" + orderQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("receivingAddress"), "%" + orderQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("postalCode"), "%" + orderQuery.getKeyword() + "%")
                ));
            }
            if (orderQuery.getCustomerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), orderQuery.getCustomerId()));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderMaster saveOrderMaster(OrderMaster orderMaster) {
        return orderMasterRepository.save(orderMaster);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteOrderMasterById(Long id) {
        orderMasterRepository.deleteById(id);
    }
}
