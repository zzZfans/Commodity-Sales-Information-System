package com.zfans.web;

import com.zfans.entity.Commodity;
import com.zfans.service.BrandService;
import com.zfans.service.ClassificationService;
import com.zfans.service.CommodityService;
import com.zfans.service.SupplierService;
import com.zfans.vo.CommodityQuery;
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
 * @date 2020/05/17 19:32
 */
@Controller
public class CommodityController {
    @Autowired
    CommodityService commodityService;
    @Autowired
    BrandService brandService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    SupplierService supplierService;

    @GetMapping("/commodities")
    public String commodities(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
                                      Pageable pageable, Model model) {
        model.addAttribute("brands", brandService.listBrand());
        model.addAttribute("classifications", classificationService.listClassification());
        model.addAttribute("suppliers", supplierService.listSupplier());
        model.addAttribute("commodities", commodityService.listCommodity());
        model.addAttribute("page", commodityService.listCommodity(pageable));
        return "commodities";
    }

    @PostMapping("/commodities")
    public String post(@Valid Commodity commodity, BindingResult result, RedirectAttributes attributes, Model model) {
        commodity.setBrand(brandService.getBrandById(commodity.getBrand().getId()));
        commodity.setClassification(classificationService.getClassificationById(commodity.getClassification().getId()));
        commodity.setSupplierList(supplierService.listSupplier(commodity.getSupplierIds()));
        commodity.setNameSpecificationModel(commodity.getName() + "-" + commodity.getSpecification() + "-" + commodity.getModel());
        Commodity t1 = new Commodity();
        if (commodity.getId() != null) {
            Commodity t2 = new Commodity();
            t2.setId(commodity.getId());
            t1.setId(commodity.getId());
            t1.setCode(commodityService.getCommodityById(t1.getId()).getCode());
            t1.setName(commodityService.getCommodityById(t1.getId()).getName());
            t1.setSpecification(commodityService.getCommodityById(t1.getId()).getSpecification());
            t1.setNameSpecificationModel(commodityService.getCommodityById(t1.getId()).getNameSpecificationModel());
            t1.setModel(commodityService.getCommodityById(t1.getId()).getModel());
            t1.setUnit(commodityService.getCommodityById(t1.getId()).getUnit());
            t1.setMarketValue(commodityService.getCommodityById(t1.getId()).getMarketValue());
            t1.setSalesPrice(commodityService.getCommodityById(t1.getId()).getSalesPrice());
            t1.setCostPrice(commodityService.getCommodityById(t1.getId()).getCostPrice());
            t1.setPicture(commodityService.getCommodityById(t1.getId()).getPicture());
            t1.setIntroduce(commodityService.getCommodityById(t1.getId()).getIntroduce());
            t1.setQuantity(commodityService.getCommodityById(t1.getId()).getQuantity());
            t1.setBrand(brandService.getBrandById(commodity.getBrand().getId()));
            t1.setClassification(classificationService.getClassificationById(commodity.getClassification().getId()));
            t1.setSupplierList(supplierService.listSupplier(commodity.getSupplierIds()));

            commodityService.saveCommodity(t2);
        }
        Commodity commodity1 = commodityService.getCommodityByCode(commodity.getCode());
        Commodity commodity2 = commodityService.getCommodityByNameSpecificationModel(commodity.getNameSpecificationModel());
        Commodity commodity3 = commodityService.getCommodityByPicture(commodity.getPicture());
        Commodity commodity4 = commodityService.getCommodityByIntroduce(commodity.getIntroduce());
        if (commodity1 != null) {
            result.rejectValue("name", "nameError", "该商品代码已存在！");
            if (commodity.getId() != null) {
                commodityService.saveCommodity(t1);
            }
        }
        if (commodity2 != null) {
            result.rejectValue("name", "nameError", "该商品名称规格型号已存在！");
            if (commodity.getId() != null) {
                commodityService.saveCommodity(t1);
            }
        }
        if (commodity3 != null) {
            result.rejectValue("name", "nameError", "该商品缩略图已存在！");
            if (commodity.getId() != null) {
                commodityService.saveCommodity(t1);
            }
        }
        if (commodity4 != null) {
            result.rejectValue("name", "nameError", "该商品介绍已存在！");
            if (commodity.getId() != null) {
                commodityService.saveCommodity(t1);
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.listBrand());
            model.addAttribute("classifications", classificationService.listClassification());
            model.addAttribute("suppliers", supplierService.listSupplier());
            return "commodity-input";
        }
        Commodity c = commodityService.saveCommodity(commodity);
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
        return "redirect:/commodities";
    }

    @GetMapping("/commodities/input")
    public String input(Model model) {
        model.addAttribute("brands", brandService.listBrand());
        model.addAttribute("classifications", classificationService.listClassification());
        model.addAttribute("suppliers", supplierService.listSupplier());
        model.addAttribute("commodity", new Commodity());
        return "commodity-input";
    }

    @GetMapping("/commodities/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        commodityService.deleteCommodityById(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/commodities";
    }

    @GetMapping("/commodities/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Commodity commodity = commodityService.getCommodityById(id);
        commodity.initSupplierIds();
        model.addAttribute("brands", brandService.listBrand());
        model.addAttribute("classifications", classificationService.listClassification());
        model.addAttribute("suppliers", supplierService.listSupplier());
        model.addAttribute("commodity", commodityService.getCommodityById(id));
        return "commodity-input";
    }

    @PostMapping("/commodities/search")
    public String search(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, CommodityQuery commodityQuery, Model model) {
        model.addAttribute("page", commodityService.listCommodity(pageable, commodityQuery));
        return "commodities :: commodityList";
    }

}
