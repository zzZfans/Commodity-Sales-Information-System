package com.zfans.service;

import com.zfans.dao.SupplierRepository;
import com.zfans.entity.Brand;
import com.zfans.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/12 23:35
 */
@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Supplier getSupplierById(Long id) {
        return supplierRepository.getOne(id);
    }

    public List<Supplier> listSupplier() {
        return supplierRepository.findAll();
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
