package com.zfans.web;

import com.zfans.entity.OrderDetail;
import com.zfans.service.CommodityService;
import com.zfans.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zfans
 * @date 2020/05/20 13:29
 */
@Controller
public class OrderDetailController {
    private static final List<OrderDetail> listOrderDetail = new ArrayList<>();
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    CommodityService commodityService;

    public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }

    @GetMapping("/orderDetails")
    public String orderDetails(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                       Pageable pageable, Model model) {
        model.addAttribute("page", listConvertToPage(listOrderDetail, pageable));
        return "place-order";
    }

    @PostMapping("/orderDetails")
    public String post(@Valid OrderDetail orderDetail, BindingResult result, RedirectAttributes attributes, Model model) {
        if (orderDetail.getOrderQuantity() < 1) {
            result.rejectValue("id", "Error", "商品数量不合法！");
            model.addAttribute("commodities", commodityService.listCommodity());
            return "shopping";
        }
        if (orderDetail.getTotalAmount() != null) {
            if (commodityService.getCommodityById(orderDetail.getCommodity().getId()).getQuantity()
                    < orderDetail.getOrderQuantity()) {
                result.rejectValue("id", "Error", "商品库存不足！");
                attributes.addFlashAttribute("message", "修改失败！");
            } else {
                for (OrderDetail detail : listOrderDetail) {
                    if (detail.getCommodity().getId().equals(orderDetail.getCommodity().getId())) {
                        detail.setOrderQuantity(orderDetail.getOrderQuantity());
                        detail.setTotalAmount(detail.getCommodity().getSalesPrice().multiply(BigDecimal.valueOf(detail.getOrderQuantity())));
                        attributes.addFlashAttribute("message", "修改成功！");
                        break;
                    }
                }
            }
        } else {
            boolean isExists = false;
            for (OrderDetail detail : listOrderDetail) {
                if (detail.getCommodity().getId().equals(orderDetail.getCommodity().getId())) {
                    result.rejectValue("id", "Error", "商品已存在于购物列表中！");
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                if (commodityService.getCommodityById(orderDetail.getCommodity().getId()).getQuantity()
                        < orderDetail.getOrderQuantity()) {
                    result.rejectValue("id", "Error", "商品库存不足！");
                    attributes.addFlashAttribute("message", "添加失败！");
                } else {
                    orderDetail.setCommodity(commodityService.getCommodityById(orderDetail.getCommodity().getId()));
                    orderDetail.setTotalAmount(orderDetail.getCommodity().getSalesPrice().multiply(BigDecimal.valueOf(orderDetail.getOrderQuantity())));
                    listOrderDetail.add(orderDetail);
                    attributes.addFlashAttribute("message", "添加成功！");
                }
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("commodities", commodityService.listCommodity());
            return "shopping";
        }
        return "redirect:/orderDetails";
    }

    @GetMapping("/orderDetails/shopping")
    public String input(Model model) {
        model.addAttribute("commodities", commodityService.listCommodity());
        model.addAttribute("orderDetail", new OrderDetail());
        return "shopping";
    }


    @GetMapping("/orderDetails/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        for (OrderDetail orderDetail : listOrderDetail) {
            if (orderDetail.getCommodity().getId().equals(id)) {
                listOrderDetail.remove(orderDetail);
                break;
            }
        }
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/orderDetails";
    }

    @GetMapping("/orderDetails/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        for (OrderDetail orderDetail : listOrderDetail) {
            if (orderDetail.getCommodity().getId().equals(id)) {
                model.addAttribute("orderDetail", orderDetail);
                break;
            }
        }
        model.addAttribute("commodities", commodityService.listCommodity());
        return "shopping";
    }
//
//    @PostMapping("/brands/search")
//    public String search(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.ASC)
//                                 Pageable pageable, Brand brand, Model model) {
//        model.addAttribute("page", brandService.listBrand(pageable, brand));
//        return "brands :: brandList";
//    }
}
