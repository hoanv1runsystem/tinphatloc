package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ThietBiDto;
import com.vn.backend.response.ThietBiResponse;
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
public class ThietBiController {

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private NhienLieuService nhienLieuService;

    @RequestMapping("/thietBi")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maThietBi", "tenThietBi").ascending());
        ThietBiResponse response = thietBiService.search(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("thietBis", response);
        model.addAttribute("keyword", keyword);
        return "/backend/thietBi/viewThietBi";

    }

    public String detail(Model model) {
        return "/backend/thietBi/detailThietBi";

    }

    @RequestMapping(value = "/thietBi/add", method = RequestMethod.GET)
    public String add(Model model) {
        ThietBiDto objectDto = new ThietBiDto();
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("thietBi", objectDto);

        return "/backend/thietBi/addThietBi";

    }

    @RequestMapping(value = "/thietBi/add", method = RequestMethod.POST)
    public String doAdd(ThietBiDto objectDto, Model model, RedirectAttributes redirect) {

        ThietBiResponse response = thietBiService.add(objectDto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/thietBi/";
        } else {
            model.addAttribute("message", response.getMessage());
            objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
            model.addAttribute("thietBi", objectDto);
            return "/backend/thietBi/addThietBi";
        }
    }

    @RequestMapping(value = "/thietBi/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        ThietBiDto objectDto = thietBiService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("thietBi", objectDto);

        return "/backend/thietBi/editThietBi";

    }

    @RequestMapping(value = "/thietBi/update", method = RequestMethod.POST)
    public String doUpdate(ThietBiDto objectDto, Model model, RedirectAttributes redirect) {

        try {

            ThietBiResponse response = thietBiService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/thietBi/";
            } else {
                model.addAttribute("message", response.getMessage());
                objectDto.setNhienLieus(nhienLieuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
                model.addAttribute("thietBi", objectDto);
                return "/backend/thietBi/editThietBi";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/thietBi/";
    }

    @RequestMapping(value = "/thietBi/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            thietBiService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/thietBi/";
        } catch (Exception e) {
            return "/backend/thietBi/";
        }
    }

}
