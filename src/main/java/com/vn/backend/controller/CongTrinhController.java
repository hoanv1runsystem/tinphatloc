package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CongTrinhDto;
import com.vn.backend.response.CongTrinhResponse;
import com.vn.backend.service.ChuDauTuService;
import com.vn.backend.service.CongTrinhService;
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
public class CongTrinhController {

    @Autowired
    private CongTrinhService congTrinhService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ChuDauTuService chuDauTuService;

    @RequestMapping("/congTrinh")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("maCongtrinh", "tenCongTrinh").ascending());
        CongTrinhResponse response = congTrinhService.getPaggingAll(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);

        model.addAttribute("congTrinhs", response);
        model.addAttribute("keyword", keyword);
        return "/backend/congTrinh/viewCongTrinh";

    }

    public String detail(Model model) {
        return "/backend/congTrinh/detailCongTrinh";

    }

    @RequestMapping(value = "/congTrinh/add", method = RequestMethod.GET)
    public String add(Model model) {
        CongTrinhDto objectDto = new CongTrinhDto();
        objectDto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setChuDauTus(chuDauTuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("congTrinh", objectDto);

        return "/backend/congTrinh/addCongTrinh";

    }

    @RequestMapping(value = "/congTrinh/add", method = RequestMethod.POST)
    public String doAdd(CongTrinhDto dto, Model model, RedirectAttributes redirect) {

        dto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setChuDauTus(chuDauTuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));

        CongTrinhResponse response = congTrinhService.addValidate(dto);
        if (Constant.STATUS_SUCCSESS == response.getStatus()) {
            redirect.addFlashAttribute("successMessage", response.getMessage());
            return "redirect:/admin/congTrinh/";
        } else {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("congTrinh", dto);
            return "/backend/congTrinh/addCongTrinh";
        }
    }

    @RequestMapping(value = "/congTrinh/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        CongTrinhDto objectDto = congTrinhService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setChuDauTus(chuDauTuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("congTrinh", objectDto);
        return "/backend/congTrinh/editCongTrinh";

    }

    @RequestMapping(value = "/congTrinh/update", method = RequestMethod.POST)
    public String doUpdate(CongTrinhDto objectDto, Model model, RedirectAttributes redirect) {
        objectDto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setChuDauTus(chuDauTuService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        try {
            CongTrinhResponse response = congTrinhService.update(objectDto);
            if (Constant.STATUS_SUCCSESS == response.getStatus()) {
                redirect.addFlashAttribute("successMessage", response.getMessage());
                return "redirect:/admin/congTrinh/";
            } else {
                model.addAttribute("message", response.getMessage());
                model.addAttribute("congTrinh", objectDto);
                return "/backend/congTrinh/editCongTrinh";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/congTrinh/";
    }

    @RequestMapping(value = "/congTrinh/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            congTrinhService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/congTrinh/";
        } catch (Exception e) {
            return "/backend/congTrinh/";
        }
    }

}
