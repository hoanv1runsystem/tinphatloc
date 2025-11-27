package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhienLieuDto;
import com.vn.backend.response.NhienLieuResponse;
import com.vn.backend.service.NhienLieuService;
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
public class NhienLieuController {

    @Autowired
    private NhienLieuService nhienLieuService;

    @RequestMapping("/nhienLieu")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maNhienLieu", "tenNhienLieu").ascending());
        NhienLieuResponse response = nhienLieuService.searchView(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("nhienLieus", response);
        model.addAttribute("keyword", keyword);
        return "/backend/nhienLieu/viewNhienLieu";

    }

    public String detail(Model model) {
        return "/backend/nhienLieu/detailNhienLieu";

    }

    @RequestMapping(value = "/nhienLieu/add", method = RequestMethod.GET)
    public String add(Model model) {

        NhienLieuDto objectDto = new NhienLieuDto();
        model.addAttribute("nhienLieu", objectDto);

        return "/backend/nhienLieu/addNhienLieu";

    }

    @RequestMapping(value = "/nhienLieu/add", method = RequestMethod.POST)
    public String doAdd(NhienLieuDto dto, Model model, RedirectAttributes redirect) {

        NhienLieuResponse response = nhienLieuService.add(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/nhienLieu/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("nhienLieu", dto);
            return "/backend/nhienLieu/addNhienLieu";
        }
    }

    @RequestMapping(value = "/nhienLieu/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) throws Exception {
        NhienLieuDto objectDto = nhienLieuService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        model.addAttribute("nhienLieu", objectDto);
        return "/backend/nhienLieu/editNhienLieu";

    }

    @RequestMapping(value = "/nhienLieu/update", method = RequestMethod.POST)
    public String doUpdate(NhienLieuDto objectDto, Model model, RedirectAttributes redirect) {

        try {
            NhienLieuResponse response = nhienLieuService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/nhienLieu/";
            } else {
                model.addAttribute("message", response.getMessage());
                model.addAttribute("nhienLieu", objectDto);
                return "/backend/nhienLieu/editNhienLieu";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/nhienLieu/";
    }

    @RequestMapping(value = "/nhienLieu/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            nhienLieuService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/nhienLieu/";
        } catch (Exception e) {
            return "/backend/nhienLieu/";
        }
    }

}
