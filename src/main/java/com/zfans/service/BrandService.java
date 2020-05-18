package com.zfans.service;

import com.zfans.dao.BrandRepository;
import com.zfans.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    public Brand getBrandByWebsite(String website) {
        return brandRepository.findByWebsite(website);
    }

    public Brand getBrandByLogo(String logo) {
        return brandRepository.findByLogo(logo);
    }

    public Brand getBrandById(Long id) {
        return brandRepository.getOne(id);
    }

    public List<Brand> listBrand() {
        return brandRepository.findAll();
    }

    public Page<Brand> listBrand(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Page<Brand> listBrand(Pageable pageable, Brand brand) {
        return brandRepository.findAll((Specification<Brand>) (root, criteriaQuery, criteriaBuilder) -> {
            if (!"".equals(brand.getName()) && brand.getName() != null) {
                criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + brand.getName() + "%"),
                        criteriaBuilder.like(root.get("website"), "%" + brand.getName() + "%"),
                        criteriaBuilder.like(root.get("logo"), "%" + brand.getName() + "%")));
            }
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
