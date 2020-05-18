package com.zfans.web;

import com.zfans.entity.Supplier;
import com.zfans.service.SupplierService;
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
 * @date 2020/05/15 11:20
 */
@Controller
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliers(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", supplierService.listSupplier(pageable));
        return "suppliers";
    }

    @PostMapping("/suppliers")
    public String post(@Valid Supplier supplier, BindingResult result, RedirectAttributes attributes) {
        Supplier t1 = new Supplier();
        if (supplier.getId() != null) {
            Supplier t2 = new Supplier();
            t2.setId(supplier.getId());
            t1.setId(supplier.getId());
            t1.setName(supplierService.getSupplierById(t1.getId()).getName());
            t1.setContact(supplierService.getSupplierById(t1.getId()).getContact());
            t1.setContactNumber(supplierService.getSupplierById(t1.getId()).getContactNumber());
            t1.setSupplierProfile(supplierService.getSupplierById(t1.getId()).getSupplierProfile());
            supplierService.saveSupplier(t2);
        }
        Supplier supplier1 = supplierService.getSupplierByName(supplier.getName());
        Supplier supplier2 = supplierService.getSupplierBySupplierProfile(supplier.getSupplierProfile());
        if (supplier1 != null) {
            result.rejectValue("name", "nameError", "该供应商已存在！");
            if (supplier.getId() != null) {
                supplierService.saveSupplier(t1);
            }
        }
        if (supplier2 != null) {
            result.rejectValue("name", "nameError", "该供应商简介已存在！");
            if (supplier.getId() != null) {
                supplierService.saveSupplier(t1);
            }
        }
        if (result.hasErrors()) {
            return "supplier-input";
        }
        Supplier s = supplierService.saveSupplier(supplier);
        if (s == null) {
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
        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/input")
    public String input(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier-input";
    }

    @GetMapping("/suppliers/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        supplierService.deleteSupplierById(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "supplier-input";
    }

    @PostMapping("/suppliers/search")
    public String search(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, Supplier supplier, Model model) {
        model.addAttribute("page", supplierService.listSupplier(pageable, supplier));
        return "suppliers :: supplierList";
    }

}
