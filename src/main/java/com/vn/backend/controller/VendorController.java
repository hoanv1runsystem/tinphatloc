package com.vn.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.backend.dto.VendorDto;
import com.vn.backend.response.VendorResponse;
import com.vn.backend.service.VendorService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@RequestMapping("/vendor")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("name").ascending());
		VendorResponse result = vendorService.getPagging(0, paging);

		model.addAttribute("vendors", result);
		return "/backend/vendor/viewVendor";

	}

	public String detail(Model model) {
		return "/backend/vendor/detailVendor";

	}

	@RequestMapping(value = "/vendor/add", method = RequestMethod.GET)
	public String add(Model model) {
		VendorDto vendor = new VendorDto();
		model.addAttribute("vendor", vendor);

		return "/backend/vendor/addVendor";

	}

	@RequestMapping(value = "/vendor/add", method = RequestMethod.POST)
	public String doAdd(VendorDto dto, Model model, RedirectAttributes redirect) throws Exception {

		VendorResponse resutl = vendorService.addValidate(dto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/vendor/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("vendor", dto);
			return "/backend/vendor/addVendor";
		}

	}

	@RequestMapping(value = "/vendor/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		VendorDto vendor = vendorService.getDetail(id, 0);

		model.addAttribute("vendor", vendor);
		return "/backend/vendor/editVendor";

	}

	@RequestMapping(value = "/vendor/update", method = RequestMethod.POST)
	public String doUpdate(VendorDto dto, Model model, RedirectAttributes redirect) throws Exception {
		vendorService.update(dto);
		redirect.addFlashAttribute("successMessage", Message.ADD_SUCCESS);
		return "redirect:/admin/vendor/";

	}

	@RequestMapping(value = "/vendor/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			vendorService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/vendor/";
		} catch (Exception e) {
			redirect.addFlashAttribute("successMessage", Message.DELETE_FAILURE);
			return "redirect:/admin/vendor/";
		}
	}

}
