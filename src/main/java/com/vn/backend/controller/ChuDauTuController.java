package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ChuDauTuDto;
import com.vn.backend.response.ChuDauTuResponse;
import com.vn.backend.service.ChuDauTuService;
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
public class ChuDauTuController {

    @Autowired
    private ChuDauTuService chuDauTuService;

    @RequestMapping("/chuDauTu")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maChuDauTu", "tenChuDauTu").ascending());
        ChuDauTuResponse response = chuDauTuService.searchView(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("chuDauTus", response);
        model.addAttribute("keyword", keyword);
        return "/backend/chuDauTu/viewChuDauTu";

    }

    public String detail(Model model) {
        return "/backend/chuDauTu/detailChuDauTu";

    }

    @RequestMapping(value = "/chuDauTu/add", method = RequestMethod.GET)
    public String add(Model model) {
        ChuDauTuDto objectDto = new ChuDauTuDto();
        model.addAttribute("chuDauTu", objectDto);

        return "/backend/chuDauTu/addChuDauTu";

    }

    @RequestMapping(value = "/chuDauTu/add", method = RequestMethod.POST)
    public String doAdd(ChuDauTuDto dto, Model model, RedirectAttributes redirect) {

        ChuDauTuResponse response = chuDauTuService.add(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/chuDauTu/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("chuDauTu", dto);
            return "/backend/chuDauTu/addChuDauTu";
        }
    }

    @RequestMapping(value = "/chuDauTu/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        ChuDauTuDto objectDto = chuDauTuService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);

        model.addAttribute("chuDauTu", objectDto);
        return "/backend/chuDauTu/editChuDauTu";

    }

    @RequestMapping(value = "/chuDauTu/update", method = RequestMethod.POST)
    public String doUpdate(ChuDauTuDto objectDto, Model model, RedirectAttributes redirect) {

        try {
            ChuDauTuResponse response = chuDauTuService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/chuDauTu/";
            } else {
                model.addAttribute("message", response.getMessage());
                model.addAttribute("chuDauTu", objectDto);
                return "/backend/chuDauTu/editChuDauTu";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/chuDauTu/";
    }

    @RequestMapping(value = "/chuDauTu/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            chuDauTuService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/chuDauTu/";
        } catch (Exception e) {
            return "/backend/chuDauTu/";
        }
    }

}
