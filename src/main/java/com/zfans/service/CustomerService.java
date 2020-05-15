package com.zfans.service;

import com.zfans.dao.CustomerRepository;
import com.zfans.entity.Brand;
import com.zfans.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/15 15:26
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer getCustomerByCustomerNumber(String customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.getOne(id);
    }


    public List<Customer> listCustomer() {
        return customerRepository.findAll();
    }

    public Page<Customer> listCustomer(Pageable pageable, Customer customer) {
        return customerRepository.findAll((Specification<Customer>) (root, criteriaQuery, criteriaBuilder) -> {
            if (!"".equals(customer.getName()) && customer.getName() != null) {
                criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + customer.getName() + "%"),
                        criteriaBuilder.like(root.get("customerNumber"), "%" + customer.getName() + "%"),
                        criteriaBuilder.like(root.get("phoneNumber"), "%" + customer.getName() + "%"),
                        criteriaBuilder.like(root.get("address"), "%" + customer.getName() + "%")));
            }
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

}
