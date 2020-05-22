package com.zfans.web;

import com.zfans.entity.Customer;
import com.zfans.service.CustomerService;
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
 * @date 2020/05/15 17:54
 */
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String customers(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", customerService.listCustomer(pageable));
        return "customers";
    }

    @PostMapping("/customers")
    public String post(@Valid Customer customer, BindingResult result, RedirectAttributes attributes) {
        Customer t1 = new Customer();
        if (customer.getId() != null) {
            Customer t2 = new Customer();
            t2.setId(customer.getId());
            t1.setId(customer.getId());
            t1.setName(customerService.getCustomerById(t1.getId()).getName());
            t1.setCustomerNumber(customerService.getCustomerById(t1.getId()).getCustomerNumber());
            t1.setPhoneNumber(customerService.getCustomerById(t1.getId()).getPhoneNumber());
            t1.setAddress(customerService.getCustomerById(t1.getId()).getAddress());
            customerService.saveCustomer(t2);
        }
        Customer customer1 = customerService.getCustomerByName(customer.getName());
        Customer customer2 = customerService.getCustomerByCustomerNumber(customer.getCustomerNumber());
        Customer customer3 = customerService.getCustomerByPhoneNumber(customer.getPhoneNumber());
        if (customer1 != null) {
            result.rejectValue("name", "nameError", "该客户已存在！");
            if (customer.getId() != null) {
                customerService.saveCustomer(t1);
            }
        }
        if (customer2 != null) {
            result.rejectValue("name", "nameError", "该客户号已存在！");
            if (customer.getId() != null) {
                customerService.saveCustomer(t1);
            }
        }
        if (customer3 != null) {
            result.rejectValue("name", "nameError", "该客户电话号码已存在！");
            if (customer.getId() != null) {
                customerService.saveCustomer(t1);
            }
        }
        if (result.hasErrors()) {
            return "customer-input";
        }
        Customer c = customerService.saveCustomer(customer);
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
        return "redirect:/customers";
    }

    @GetMapping("/customers/input")
    public String input(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-input";
    }

    @GetMapping("/customers/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        customerService.deleteCustomerById(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/customers";
    }

    @GetMapping("/customers/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer-input";
    }

    @PostMapping("/customers/search")
    public String search(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                 Pageable pageable, Customer customer, Model model) {
        model.addAttribute("page", customerService.listCustomer(pageable, customer));
        return "customers :: customerList";
    }
}
