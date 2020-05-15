package com.zfans.dao;

import com.zfans.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zfans
 * @date 2020/05/15 15:21
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
