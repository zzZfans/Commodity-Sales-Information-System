package com.zfans.web;

import com.zfans.entity.Classification;
import com.zfans.service.ClassificationService;
import com.zfans.vo.ClassificationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/16 19:47
 */
@Controller
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;

    @GetMapping("/classifications")
    public String classifications(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                          Pageable pageable, Model model) {
        model.addAttribute("superClassifications", classificationService.listClassification());
        model.addAttribute("page", classificationService.listClassification(pageable));
        return "classifications";
    }

    @PostMapping("/classifications")
    public String post(@Valid Classification classification, BindingResult result, RedirectAttributes attributes, Model model) {
        Classification t1 = new Classification();
        if (classification.getId() != null) {
            Classification t2 = new Classification();
            t2.setId(classification.getId());
            t1.setId(classification.getId());
            t1.setName(classificationService.getClassificationById(t1.getId()).getName());
            t1.setPicture(classificationService.getClassificationById(t1.getId()).getPicture());
            classificationService.saveClassification(t2);
        }
        Classification classification1 = classificationService.getClassificationByName(classification.getName());
        Classification classification2 = classificationService.getClassificationByPicture(classification.getPicture());
        if (classification1 != null) {
            result.rejectValue("name", "nameError", "该分类已存在！");
            if (classification.getId() != null) {
                classificationService.saveClassification(t1);
            }
        }
        if (classification2 != null) {
            result.rejectValue("name", "nameError", "该分类图片已存在！");
            if (classification.getId() != null) {
                classificationService.saveClassification(t1);
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("superClassifications", classificationService.listClassification());
            return "classification-input";
        }
        if (classification.getSuperClassification().getId().equals(-1L)) {
            classification.setSuperClassification(null);
        }
        Classification c = classificationService.saveClassification(classification);
        if (c == null) {
            if (t1.getId() != null) {
                attributes.addFlashAttribute("message", "修改失败！");
            } else {
                attributes.addFlashAttribute("message", "添加失败！");
            }
        } else {
            if (t1.getId() != null) {
                attributes.addFlashAttribute("message", "修改成功！");
            } else {
                attributes.addFlashAttribute("message", "添加成功！");
            }
        }
        return "redirect:/classifications";
    }

    @GetMapping("/classifications/input")
    public String input(Model model) {
        model.addAttribute("superClassifications", classificationService.listClassification());
        model.addAttribute("classification", new Classification());
        return "classification-input";
    }

    @GetMapping("/classifications/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        classificationService.deleteClassificationById(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/classifications";
    }

    @GetMapping("/classifications/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        List<Classification> classifications = new ArrayList<>();
        classificationService.listClassification().forEach(ele -> {
            if (!ele.getId().equals(id)) {
                classifications.add(ele);
            }
        });
        model.addAttribute("superClassifications", classifications);
        model.addAttribute("classification", classificationService.getClassificationById(id));
        return "classification-input";
    }

    @PostMapping("/classifications/search")
    public String search(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, ClassificationQuery classificationQuery, Model model) {
        model.addAttribute("page", classificationService.listClassification(pageable, classificationQuery));
        return "classifications :: classificationList";
    }
}
