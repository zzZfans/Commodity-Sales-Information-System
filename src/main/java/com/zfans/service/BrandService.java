package com.zfans.service;

import com.zfans.dao.BrandRepository;
import com.zfans.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/12 20:34
 */
@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;


    public Brand getBrandById(Long id) {
        return brandRepository.getOne(id);
    }


    public List<Brand> listBrand() {
        return brandRepository.findAll();
    }


    public Page<Brand> listBrand(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Transactional
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
