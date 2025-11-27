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

import com.vn.backend.dto.LogoDto;
import com.vn.backend.response.LogoResponse;
import com.vn.backend.service.LogoService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class LogoController {

	@Autowired
	private LogoService logoService;

	@RequestMapping("/logo")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("id").ascending());
		LogoResponse result = logoService.getPagging(0, paging);

		model.addAttribute("logos", result);
		return "/backend/logo/viewLogo";

	}

	public String detail(Model model) {
		return "/backend/logo/detailLogo";

	}

	@RequestMapping(value = "/logo/add", method = RequestMethod.GET)
	public String add(Model model) {
		LogoDto logo = new LogoDto();
		model.addAttribute("logo", logo);

		return "/backend/logo/addLogo";

	}

	@RequestMapping(value = "/logo/add", method = RequestMethod.POST)
	public String doAdd(LogoDto dto, Model model, RedirectAttributes redirect) throws Exception {

		LogoResponse resutl = logoService.addValidate(dto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/logo/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("logo", dto);
			return "/backend/logo/addLogo";
		}

	}

	@RequestMapping(value = "/logo/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		LogoDto logo = logoService.getDetail(id, 0);

		model.addAttribute("logo", logo);
		return "/backend/logo/editLogo";

	}

	@RequestMapping(value = "/logo/update", method = RequestMethod.POST)
	public String doUpdate(LogoDto dto, Model model, RedirectAttributes redirect) throws Exception {
		logoService.update(dto);
		redirect.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
		return "redirect:/admin/logo/";

	}

	@RequestMapping(value = "/logo/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			logoService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/logo/";
		} catch (Exception e) {
			redirect.addFlashAttribute("successMessage", Message.DELETE_FAILURE);
			return "redirect:/admin/logo/";
		}
	}

}
