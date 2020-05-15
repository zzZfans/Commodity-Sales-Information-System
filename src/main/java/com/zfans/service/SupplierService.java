package com.zfans.service;

import com.zfans.dao.SupplierRepository;
import com.zfans.entity.Brand;
import com.zfans.entity.Supplier;
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
 * @date 2020/05/12 23:35
 */
@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Supplier getSupplierByName(String name) {
        return supplierRepository.findByName(name);
    }

    public Supplier getSupplierBySupplierProfile(String website) {
        return supplierRepository.findBySupplierProfile(website);
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.getOne(id);
    }

    public List<Supplier> listSupplier() {
        return supplierRepository.findAll();
    }

    public Page<Supplier> listSupplier(Pageable pageable, Supplier supplier) {
        return supplierRepository.findAll(new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(supplier.getName()) && supplier.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + supplier.getName() + "%"));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        }, pageable);
    }

    public Page<Supplier> listSupplier(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Transactional
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteSupplierById(Long id) {
        supplierRepository.deleteById(id);
    }
}
