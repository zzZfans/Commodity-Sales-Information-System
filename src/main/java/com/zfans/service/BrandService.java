package com.zfans.service;

import com.zfans.dao.BrandRepository;
import com.zfans.entity.Brand;
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


    public Page<Brand> listBrand(Pageable pageable, Brand brand) {
        return brandRepository.findAll(new Specification<Brand>() {
            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(brand.getName()) && brand.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + brand.getName() + "%"));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        }, pageable);
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
