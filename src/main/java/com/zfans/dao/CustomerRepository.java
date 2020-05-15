package com.zfans.dao;

import com.zfans.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zfans
 * @date 2020/05/15 15:21
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByName(String name);
    Customer findByCustomerNumber(String customerNumber);
    Customer findByPhoneNumber(String phoneNumber);
}
