package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhapNhienLieuDto;
import com.vn.backend.response.NhapNhienLieuResponse;
import com.vn.backend.service.NhanVienService;
import com.vn.backend.service.NhapNhienLieuService;
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
public class NhapNhienLieuController {

    @Autowired
    private NhapNhienLieuService nhapNhienLieuService;

    @Autowired
    private NhienLieuService nhienLieuService;

    @Autowired
    private NhanVienService nhanVienService;

    @RequestMapping("/nhapNhienLieu")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, CommonConstant.PAGE_SIZE,
            Sort.by("ngayNhap", "maNhienLieu").ascending());

        NhapNhienLieuResponse contacts = nhapNhienLieuService.search(keyword, Constant.DELETE_FLAG_ACTIVE, paging);
        model.addAttribute("nhapNhienLieus", contacts);
        model.addAttribute("keyword", keyword);
        return "/backend/nhapNhienLieu/viewNhapNhienLieu";
    }

    @RequestMapping(value = "/nhapNhienLieu/add", method = RequestMethod.GET)
    public String add(Model model) {
        NhapNhienLieuDto objectDto = new NhapNhienLieuDto();
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("nhapNhienLieu", objectDto);

        return "/backend/nhapNhienLieu/addNhapNhienLieu";

    }

    @RequestMapping(value = "/nhapNhienLieu/add", method = RequestMethod.POST)
    public String doAdd(NhapNhienLieuDto dto, Model model, RedirectAttributes redirect) {

        NhapNhienLieuResponse response = nhapNhienLieuService.add(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute(Message.SUCCESS, response.getMessage());
            return "redirect:/admin/nhapNhienLieu/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("nhapNhienLieu", dto);
            return "/backend/nhapNhienLieu/add";
        }

    }

    @RequestMapping(value = "/nhapNhienLieu/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        NhapNhienLieuDto objectDto = nhapNhienLieuService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("nhapNhienLieu", objectDto);
        return "/backend/nhapNhienLieu/editNhapNhienLieu";

    }

    @RequestMapping(value = "/nhapNhienLieu/update", method = RequestMethod.POST)
    public String doUpdate(NhapNhienLieuDto dto, Model model, RedirectAttributes redirect) {
        NhapNhienLieuResponse response = nhapNhienLieuService.update(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute(Message.SUCCESS, response.getMessage());
            return "redirect:/admin/nhapNhienLieu/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("nhapNhienLieu", dto);
            return "/backend/nhapNhienLieu/update";
        }
    }

    @RequestMapping(value = "/nhapNhienLieu/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            nhapNhienLieuService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/nhapNhienLieu/";
        } catch (Exception e) {
            return "/backend/nhapNhienLieu/editNhapNhienLieu";
        }
    }
}
