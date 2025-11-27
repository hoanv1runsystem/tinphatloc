package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ChiPhiDto;
import com.vn.backend.response.ChiPhiResponse;
import com.vn.backend.service.ChiPhiService;
import com.vn.backend.service.CongTrinhService;
import com.vn.backend.service.NhanVienService;
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
public class ChiPhiController {

    @Autowired
    private ChiPhiService chiPhiService;

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private CongTrinhService congTrinhService;

    @RequestMapping("/chiPhi")
    public String view(@RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, Model model) {

        Pageable paging = PageRequest.of(page, CommonConstant.PAGE_SIZE, Sort.by("ngayChungTu", "soPhieu").ascending());
        ChiPhiResponse response = chiPhiService.search(keyword, CommonConstant.DELETE_FLAG_ENABLE, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("chiPhis", response);
        return "/backend/chiPhi/viewChiPhi";

    }

    public String detail(Model model) {
        return "/backend/chiPhi/detailChiPhi";

    }

    @RequestMapping(value = "/chiPhi/add", method = RequestMethod.GET)
    public String add(Model model) {
        ChiPhiDto objectDto = new ChiPhiDto();
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("chiPhi", objectDto);

        return "/backend/chiPhi/addChiPhi";

    }

    @RequestMapping(value = "/chiPhi/add", method = RequestMethod.POST)
    public String doAdd(ChiPhiDto dto, Model model, RedirectAttributes redirect) {

        //		CustomerResponse resutl = customerService.addValidate(dto);
        //		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
        //			redirect.addFlashAttribute("successMessage", resutl.getMessage());
        //			return "redirect:/admin/category/";
        //		} else {
        //			model.addAttribute("message", resutl.getMessage());
        //			model.addAttribute("customer", dto);
        //			return "/backend/customer/addCustomer";
        //		}

        chiPhiService.add(dto);
        redirect.addFlashAttribute(Message.SUCCESS, Message.ADD_SUCCESS);
        return "redirect:/admin/chiPhi/";
    }

    @RequestMapping(value = "/chiPhi/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        ChiPhiDto objectDto = chiPhiService.getDetail(id, CommonConstant.DELETE_FLAG_ENABLE);
        objectDto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        objectDto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("chiPhi", objectDto);
        return "/backend/chiPhi/editChiPhi";

    }

    @RequestMapping(value = "/chiPhi/update", method = RequestMethod.POST)
    public String doUpdate(ChiPhiDto objectDto, Model model, RedirectAttributes redirect) {

        try {
            chiPhiService.update(objectDto);
            redirect.addFlashAttribute(Message.SUCCESS, Message.UPDATE_SUCCESS);
            return "redirect:/admin/chiPhi/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/chiPhi/";
    }

    @RequestMapping(value = "/chiPhi/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            chiPhiService.delete(id);
            redirect.addFlashAttribute(Message.SUCCESS, Message.DELETE_SUCCESS);
            return "redirect:/admin/chiPhi/";
        } catch (Exception e) {
            return "/backend/chiPhi/";
        }
    }

}
