package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.KhachHangDto;
import com.vn.backend.response.KhachHangResponse;
import com.vn.backend.service.KhachHangService;
import com.vn.utils.Constant;
import com.vn.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @RequestMapping("/khachHang")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maKhachHang", "tenKhachHang").ascending());
        KhachHangResponse response = khachHangService.searchView(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("khachHangs", response);
        model.addAttribute("keyword", keyword);
        return "/backend/khachHang/viewKhachHang";

    }

    public String detail(Model model) {
        return "/backend/khachHang/detailKhachHang";

    }

    @RequestMapping(value = "/khachHang/add", method = RequestMethod.GET)
    public String add(Model model) {
        KhachHangDto objectDto = new KhachHangDto();
        model.addAttribute("khachHang", objectDto);

        return "/backend/khachHang/addKhachHang";

    }

    @RequestMapping(value = "/khachHang/add", method = RequestMethod.POST)
    public String doAdd(KhachHangDto dto, Model model, RedirectAttributes redirect) {

        KhachHangResponse response = khachHangService.add(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/khachHang/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("khachHang", dto);
            return "/backend/khachHang/addKhachHang";
        }
    }

    @RequestMapping(value = "/khachHang/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        KhachHangDto objectDto = khachHangService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);

        model.addAttribute("khachHang", objectDto);
        return "/backend/khachHang/editKhachHang";

    }

    @RequestMapping(value = "/khachHang/update", method = RequestMethod.POST)
    public String doUpdate(KhachHangDto objectDto, Model model, RedirectAttributes redirect) {

        try {
            KhachHangResponse response = khachHangService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/khachHang/";
            } else {
                model.addAttribute("message", response.getMessage());
                model.addAttribute("khachHang", objectDto);
                return "/backend/khachHang/editKhachHang";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/khachHang/";
    }

    @RequestMapping(value = "/khachHang/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            khachHangService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/khachHang/";
        } catch (Exception e) {
            return "/backend/khachHang/";
        }
    }

}
