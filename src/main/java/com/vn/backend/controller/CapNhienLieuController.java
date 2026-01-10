package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CapNhienLieuDto;
import com.vn.backend.response.CapNhienLieuResponse;
import com.vn.backend.service.CapNhienLieuService;
import com.vn.backend.service.CongTrinhService;
import com.vn.backend.service.NhanVienService;
import com.vn.backend.service.NhienLieuService;
import com.vn.backend.service.ThietBiService;
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
public class CapNhienLieuController {

    @Autowired
    private CapNhienLieuService capNhienLieuService;

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private CongTrinhService congTrinhService;

    @Autowired
    private NhienLieuService nhienLieuService;

    @RequestMapping(value = "/capNhienLieu", method = RequestMethod.GET)
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, CommonConstant.PAGE_SIZE,
            Sort.by("ngayCap", "maThietBi", "maCongTrinh").ascending());

        CapNhienLieuResponse contacts = capNhienLieuService.search(keyword, Constant.DELETE_FLAG_ACTIVE, paging);
        model.addAttribute("capNhienLieus", contacts);
        model.addAttribute("keyword", keyword);
        return "backend/capNhienLieu/viewCapNhienLieu";
    }

    @RequestMapping(value = "/capNhienLieu/add", method = RequestMethod.GET)
    public String add(Model model) {
        CapNhienLieuDto objectDto = new CapNhienLieuDto();
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));

        model.addAttribute("capNhienLieu", objectDto);

        return "/backend/capNhienLieu/addCapNhienLieu";

    }

    @RequestMapping(value = "/capNhienLieu/add", method = RequestMethod.POST)
    public String doAdd(CapNhienLieuDto dto, Model model, RedirectAttributes redirect) {
        capNhienLieuService.add(dto);
        redirect.addFlashAttribute(Message.SUCCESS, Message.ADD_SUCCESS);
        return "redirect:/admin/capNhienLieu/";

    }

    @RequestMapping(value = "/capNhienLieu/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        CapNhienLieuDto objectDto = capNhienLieuService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("capNhienLieu", objectDto);
        return "/backend/capNhienLieu/editCapNhienLieu";

    }

    @RequestMapping(value = "/capNhienLieu/update", method = RequestMethod.POST)
    public String doUpdate(CapNhienLieuDto dto, Model model, RedirectAttributes redirect) {

        try {
            capNhienLieuService.update(dto);
            redirect.addFlashAttribute(Message.SUCCESS, Message.UPDATE_SUCCESS);
            return "redirect:/admin/capNhienLieu/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/capNhienLieu/";
    }

    @RequestMapping(value = "/capNhienLieu/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            capNhienLieuService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/capNhienLieu/";
        } catch (Exception e) {
            return "/backend/capNhienLieu/editCapNhienLieu";
        }
    }
}
