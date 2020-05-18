package com.zfans.web;

import com.zfans.entity.Brand;
import com.zfans.service.BrandService;
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

/**
 * @author Zfans
 * @date 2020/05/13 19:00
 */
@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/brands")
    public String brands(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, Model model) {
        model.addAttribute("page", brandService.listBrand(pageable));
        return "brands";
    }

    @PostMapping("/brands")
    public String post(@Valid Brand brand, BindingResult result, RedirectAttributes attributes) {
        Brand t1 = new Brand();
        if (brand.getId() != null) {
            Brand t2 = new Brand();
            t2.setId(brand.getId());
            t1.setId(brand.getId());
            t1.setName(brandService.getBrandById(t1.getId()).getName());
            t1.setWebsite(brandService.getBrandById(t1.getId()).getWebsite());
            t1.setLogo(brandService.getBrandById(t1.getId()).getLogo());
            brandService.saveBrand(t2);
        }
        Brand brand1 = brandService.getBrandByName(brand.getName());
        Brand brand2 = brandService.getBrandByWebsite(brand.getWebsite());
        Brand brand3 = brandService.getBrandByLogo(brand.getLogo());
        if (brand1 != null) {
            result.rejectValue("name", "nameError", "该品牌已存在！");
            if (brand.getId() != null) {
                brandService.saveBrand(t1);
            }
        }
        if (brand2 != null) {
            result.rejectValue("name", "nameError", "该网址已存在！");
            if (brand.getId() != null) {
                brandService.saveBrand(t1);
            }
        }
        if (brand3 != null) {
            result.rejectValue("name", "nameError", "该logo已存在！");
            if (brand.getId() != null) {
                brandService.saveBrand(t1);
            }
        }
        if (result.hasErrors()) {
            return "brand-input";
        }
        Brand b = brandService.saveBrand(brand);
        if (b == null) {
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
        return "redirect:/brands";
    }

    @GetMapping("/brands/input")
    public String input(Model model) {
        model.addAttribute("brand", new Brand());
        return "brand-input";
    }

    @GetMapping("/brands/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        brandService.deleteBrandById(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/brands";
    }

    @GetMapping("/brands/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.getBrandById(id));
        return "brand-input";
    }

    @PostMapping("/brands/search")
    public String search(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, Brand brand, Model model) {
        model.addAttribute("page", brandService.listBrand(pageable, brand));
        return "brands :: brandList";
    }
}
