package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhanVienDto;
import com.vn.backend.response.NhanVienResponse;
import com.vn.backend.service.NhanVienService;
import com.vn.backend.service.ReportService;
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
public class NhanVienController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private NhanVienService nhanVienService;

    @RequestMapping("/nhanVien")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maNhanVien", "tenNhanVien").ascending());
        NhanVienResponse response = nhanVienService.getPagging(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("nhanViens", response);
        model.addAttribute("keyword", keyword);
        return "/backend/nhanVien/viewNhanVien";

    }

    public String detail(Model model) {
        return "/backend/nhanVien/detailNhanVien";

    }

    @RequestMapping(value = "/nhanVien/add", method = RequestMethod.GET)
    public String add(Model model) {
        NhanVienDto objectDto = new NhanVienDto();
        model.addAttribute("nhanVien", objectDto);

        return "/backend/nhanVien/addNhanVien";

    }

    @RequestMapping(value = "/nhanVien/add", method = RequestMethod.POST)
    public String doAdd(NhanVienDto dto, Model model, RedirectAttributes redirect) {

        NhanVienResponse response = nhanVienService.add(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/nhanVien/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("nhanVien", dto);
            return "/backend/nhanVien/addNhanVien";
        }
    }

    @RequestMapping(value = "/nhanVien/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        NhanVienDto objectDto = nhanVienService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);

        model.addAttribute("nhanVien", objectDto);
        return "/backend/nhanVien/editNhanVien";

    }

    @RequestMapping(value = "/nhanVien/update", method = RequestMethod.POST)
    public String doUpdate(NhanVienDto objectDto, Model model, RedirectAttributes redirect) {

        try {

            NhanVienResponse response = nhanVienService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/nhanVien/";
            } else {
                model.addAttribute("message", response.getMessage());
                model.addAttribute("nhanVien", objectDto);
                return "/backend/nhanVien/editNhanVien";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/nhanVien/";
    }

    @RequestMapping(value = "/nhanVien/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            nhanVienService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/nhanVien/";
        } catch (Exception e) {
            return "/backend/nhanVien/";
        }
    }

}
