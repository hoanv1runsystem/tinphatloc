package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhatKyXeDto;
import com.vn.backend.response.NhatKyXeResponse;
import com.vn.backend.service.CongTrinhService;
import com.vn.backend.service.DvtService;
import com.vn.backend.service.KhachHangService;
import com.vn.backend.service.NhanVienService;
import com.vn.backend.service.NhatKyXeService;
import com.vn.backend.service.ThietBiService;
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
public class NhatKyXeController {

    @Autowired
    private NhatKyXeService nhatKyXeService;

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private CongTrinhService congTrinhService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private DvtService dvtService;

    @RequestMapping("/nhatKyXe")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("ngayChungTu", "soPhieu").ascending());
        NhatKyXeResponse response = nhatKyXeService.search(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("nhatKyXes", response);
        return "/backend/nhatKyXe/viewNhatKyXe";

    }

    public String detail(Model model) {
        return "/backend/nhatKyXe/detailNhatKyXe";

    }

    @RequestMapping(value = "/nhatKyXe/add", method = RequestMethod.GET)
    public String add(Model model) {
        NhatKyXeDto objectDto = new NhatKyXeDto();
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setDvts(dvtService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("nhatKyXe", objectDto);

        return "/backend/nhatKyXe/addNhatKyXe";

    }

    @RequestMapping(value = "/nhatKyXe/add", method = RequestMethod.POST)
    public String doAdd(NhatKyXeDto dto, Model model, RedirectAttributes redirect) {

        //		CustomerResponse resutl = customerService.addValidate(dto);
        //		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
        //			redirect.addFlashAttribute("successMessage", resutl.getMessage());
        //			return "redirect:/admin/category/";
        //		} else {
        //			model.addAttribute("message", resutl.getMessage());
        //			model.addAttribute("customer", dto);
        //			return "/backend/customer/addCustomer";
        //		}

        nhatKyXeService.add(dto);
        redirect.addFlashAttribute(Message.SUCCESS, Message.ADD_SUCCESS);
        return "redirect:/admin/nhatKyXe/";
    }

    @RequestMapping(value = "/nhatKyXe/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        NhatKyXeDto objectDto = nhatKyXeService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setDvts(dvtService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("nhatKyXe", objectDto);
        return "/backend/nhatKyXe/editNhatKyXe";

    }

    @RequestMapping(value = "/nhatKyXe/update", method = RequestMethod.POST)
    public String doUpdate(NhatKyXeDto objectDto, Model model, RedirectAttributes redirect) {

        try {
            nhatKyXeService.update(objectDto);
            redirect.addFlashAttribute(Message.SUCCESS, Message.UPDATE_SUCCESS);
            return "redirect:/admin/nhatKyXe/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/nhatKyXe/";
    }

    @RequestMapping(value = "/nhatKyXe/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            nhatKyXeService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/nhatKyXe/";
        } catch (Exception e) {
            return "/backend/nhatKyXe/";
        }
    }

}
