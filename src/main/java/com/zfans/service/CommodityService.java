package com.zfans.service;

import com.zfans.dao.CommodityRepository;
import com.zfans.dao.SupplierRepository;
import com.zfans.entity.Commodity;
import com.zfans.vo.CommodityQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/17 19:23
 */
@Service
public class CommodityService {
    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public Commodity getCommodityByCode(String code) {
        return commodityRepository.findByCode(code);
    }

    public Commodity getCommodityByNameSpecificationModel(String nameSpecificationModel) {
        return commodityRepository.findByNameSpecificationModel(nameSpecificationModel);
    }

    public Commodity getCommodityByModel(String model) {
        return commodityRepository.findByModel(model);
    }

    public Commodity getCommodityByUnit(String unit) {
        return commodityRepository.findByUnit(unit);
    }

    public Commodity getCommodityByPicture(String picture) {
        return commodityRepository.findByPicture(picture);
    }

    public Commodity getCommodityByIntroduce(String introduce) {
        return commodityRepository.findByIntroduce(introduce);
    }

    public Commodity getCommodityById(Long id) {
        return commodityRepository.getOne(id);
    }

    public List<Commodity> listCommodity() {
        return commodityRepository.findAll();
    }

    public Page<Commodity> listCommodity(Pageable pageable) {
        return commodityRepository.findAll(pageable);
    }

    public Page<Commodity> listCommodity(Pageable pageable, CommodityQuery commodityQuery) {
        return commodityRepository.findAll((Specification<Commodity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //like or like or like ...
            if (!"".equals(commodityQuery.getKeyword()) && commodityQuery.getKeyword() != null) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + commodityQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("name"), "%" + commodityQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("specification"), "%" + commodityQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("model"), "%" + commodityQuery.getKeyword() + "%")
                ));
            }
            //brand equal
            if (commodityQuery.getBrandId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), commodityQuery.getBrandId()));
            }
            //classification equal
            if (commodityQuery.getClassificationId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("classification").get("id"), commodityQuery.getClassificationId()));
            }
            //in supplier.commodityList
            if (commodityQuery.getSupplierId() != null) {
                CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("id"));
                for (Commodity commodity : supplierRepository.getOne(commodityQuery.getSupplierId()).getCommodityList()) {
                    in.value(commodity.getId());
                }
                predicates.add(in);
            }
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public Commodity saveCommodity(Commodity commodity) {
        return commodityRepository.save(commodity);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteCommodityById(Long id) {
        commodityRepository.deleteById(id);
    }
}
