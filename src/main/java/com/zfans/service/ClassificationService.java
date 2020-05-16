package com.zfans.service;

import com.zfans.dao.ClassificationRepository;
import com.zfans.entity.Brand;
import com.zfans.entity.Classification;
import com.zfans.vo.ClassificationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sun.reflect.generics.repository.ClassRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/16 19:05
 */
@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

    public Classification getClassificationByName(String name) {
        return classificationRepository.findByName(name);
    }

    public Classification getClassificationByPicture(String picture) {
        return classificationRepository.findByPicture(picture);
    }

    public Classification getClassificationById(Long id) {
        return classificationRepository.getOne(id);
    }

    public List<Classification> listClassification() {
        return classificationRepository.findAll();
    }

    public Page<Classification> listClassification(Pageable pageable, ClassificationQuery classificationQuery) {
        return classificationRepository.findAll((Specification<Classification>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"".equals(classificationQuery.getKeyword()) && classificationQuery.getKeyword() != null) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + classificationQuery.getKeyword() + "%"),
                        criteriaBuilder.like(root.get("picture"), "%" + classificationQuery.getKeyword() + "%")));

            }
            if (classificationQuery.getSuperId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("superClassification").get("id"), classificationQuery.getSuperId()));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public Classification saveClassification(Classification classification) {
        return classificationRepository.save(classification);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteClassificationById(Long id) {
        classificationRepository.deleteById(id);
    }
}
